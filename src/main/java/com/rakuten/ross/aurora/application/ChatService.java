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
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	public static final int CHAT_RESPONSE_BUFFER_SIZE = 2;
	public static final int MAX_HISTORY_SIZE = 5;

	private final TimeProvider timeProvider;
	private final ChatManager chatManager;

	private final List<ChatAdvisorSupplier> chatAdvisorSuppliers;
	private final List<ChatClientSupplier> chatClientSuppliers;

	public Conversation startConversation(ConversationStartCommand command) {
		var conversation = Conversation.of(UUID.randomUUID().toString(), timeProvider.now());
		chatManager.saveConversation(conversation);
		return conversation;
	}


	// when		who		what			where			how
	// -------------------------------------------------------------
	// now		user 	userMessage		conversation	chatOption
	// todo can we use spring to implement the history or replace with summary?
	public ChatReply chat(ChatCommand command) {
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
	}

	private ChatReply chat(ChatContext context) {
		var conversation = context.getConversation();
		var chatHistory = context.getChatHistory();
		var userMessage = context.getUserMessage();
		var chatClient = getChatClient(context);
		var advisors = getAdvisors(context);
		var reply = new StringBuffer();

		chatHistory.add(userMessage);
		chatManager.saveChatHistory(chatHistory);

		var contents = chatClient
				.prompt()
				.advisors(advisors)
				.messages(conversation.createPromptMessages())
				.messages(chatHistory.restorePromptMessages(MAX_HISTORY_SIZE))
				.messages(userMessage.createPromptMessages())
				.stream()
				.chatResponse()
				.buffer(CHAT_RESPONSE_BUFFER_SIZE)
				.map(ChatResponsesUtils::getTextContent)
				.filter(StringUtils::isNotBlank)
				.doOnNext(reply::append)
				.map(ChatMessageContent::of)
				.doOnComplete(() -> {
					var replyMessage = userMessage.reply(reply.toString());
					chatHistory.add(replyMessage);
					chatManager.saveChatHistory(chatHistory);
				});

		return ChatReply.builder()
				.contents(contents)
				.reply(Flux.defer(() -> Flux.just(chatHistory.getLast())))
				.build();
	}

	private List<Advisor> getAdvisors(ChatContext context) {
		return chatAdvisorSuppliers
				.stream()
				.filter(chatAdvisorSupplier -> chatAdvisorSupplier.support(context))
				.map(chatAdvisorSupplier -> chatAdvisorSupplier.getAdvisor(context))
				.toList();
	}

	private ChatClient getChatClient(ChatContext context) {
		return chatClientSuppliers
				.stream()
				.filter(chatAdvisorSupplier -> chatAdvisorSupplier.support(context))
				.map(chatAdvisorSupplier -> chatAdvisorSupplier.getChatClient(context))
				.findFirst()
				.orElseThrow(() -> ChatException.of("unknown how to create the chat client"));
	}


}
