package com.rakuten.ross.aurora.application;

import java.util.List;
import java.util.UUID;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.application.command.ConversationStartCommand;
import com.rakuten.ross.aurora.application.utils.ChatResponsesUtils;
import com.rakuten.ross.aurora.core.support.TimeProvider;
import com.rakuten.ross.aurora.domain.ChatManager;
import com.rakuten.ross.aurora.domain.ChatMessage;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
import com.rakuten.ross.aurora.domain.Conversation;
import com.rakuten.ross.aurora.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	public static final int CHAT_RESPONSE_BUFFER_SIZE = 8;

	private final TimeProvider timeProvider;
	private final ChatManager chatManager;

	private final List<ChatToolSupplier> chatToolSuppliers;
	private final List<ChatClientSupplier> chatClientSuppliers;
	private final List<ChatAdvisorSupplier> chatAdvisorSuppliers;

	public Conversation startConversation(ConversationStartCommand command) {
		var conversation = Conversation.of(UUID.randomUUID().toString(), timeProvider.now());
		chatManager.saveConversation(conversation);
		return conversation;
	}

	public ChatReply chat(ChatCommand command) throws ChatException {
		// when		who		what			where			how
		// -------------------------------------------------------------
		// now		user 	userMessage		conversation	chatOption
		try {
			var user = User.mock();
			var chatOption = command.getOption();
			var conversation = chatManager.getOrCreateConversation(command.getConversationId());
			var chatHistory = chatManager.getChatHistory(command.getConversationId());
			var userMessage = ChatMessage.user()
					.conversationId(command.getConversationId())
					.messageId(UUID.randomUUID().toString())
					.content(List.of(ChatMessageContent.of(command.getContent())))
					.sendTime(timeProvider.now())
					.build();
			var context = ChatContext.builder()
					.user(user)
					.userMessage(userMessage)
					.chatHistory(chatHistory)
					.chatOption(chatOption)
					.conversation(conversation)
					.build();
			return this.chat(context);
		} catch (Exception e) {
			throw ChatException.of("Something wrong when processing the chat command", e);
		}
	}

	private ChatReply chat(ChatContext context) throws ChatException {
		var conversation = context.getConversation();
		var chatHistory = context.getChatHistory();
		var userMessage = context.getUserMessage();
		var chatClient = getChatClient(context);
		var advisors = getAdvisors(context);
		var tools = getTools(context);

		chatHistory.add(userMessage);
		chatManager.saveChatHistory(chatHistory);

		var maxHistorySize = context.getChatOption().getHistorySize();
		var reply = new StringBuffer();

		var contents = chatClient
				.prompt()
				.advisors(advisors)
				.tools(tools)
				.messages(conversation.createPromptMessages())
				.messages(chatHistory.restorePromptMessages(maxHistorySize))
				.messages(userMessage.createPromptMessages())
				.stream()
				.chatResponse()
				.buffer(CHAT_RESPONSE_BUFFER_SIZE)
				.map(ChatResponsesUtils::getTextContent)
				.filter(StringUtils::isNotBlank)
				.doOnNext(reply::append)
				.map(ChatMessageContent::of);

		var message = contents.then(
				Mono.just(reply)
						.map(StringBuffer::toString)
						.map(userMessage::reply)
						.doOnNext(replyMessage -> {
							chatHistory.add(replyMessage);
							chatManager.saveChatHistory(chatHistory);
						})
		);

		return ChatReply.builder()
				.contents(contents)
				.message(message)
				.build();
	}

	private List<Advisor> getAdvisors(ChatContext context) {
		return chatAdvisorSuppliers
				.stream()
				.filter(chatAdvisorSupplier -> chatAdvisorSupplier.support(context))
				.map(chatAdvisorSupplier -> chatAdvisorSupplier.getAdvisor(context))
				.toList();
	}

	private List<ChatTool> getTools(ChatContext context) {
		return chatToolSuppliers
				.stream()
				.filter(supplier -> supplier.support(context))
				.map(supplier -> supplier.getTool(context))
				.toList();
	}

	private ChatClient getChatClient(ChatContext context) throws ChatException {
		return chatClientSuppliers
				.stream()
				.filter(chatAdvisorSupplier -> chatAdvisorSupplier.support(context))
				.map(chatAdvisorSupplier -> chatAdvisorSupplier.getChatClient(context))
				.findFirst()
				.orElseThrow(() -> ChatException.of("unknown how to create the chat client, maybe you need to add a chat client supplier?"));
	}


}
