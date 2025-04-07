package com.rakuten.ross.aurora.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	public static final int CHAT_RESPONSE_BUFFER_SIZE = 24;
	public static final String CHAT_TOOLS_CHOSEN_MODEL = "gpt-3.5-turbo";


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
		var advisors = getAdvisors(context);
		var tools = getTools(context);
		var chatClient = getChatClient(context);
		var conversation = context.getConversation();
		var chatHistory = context.getChatHistory();
		var userMessage = context.getUserMessage();

		var maxHistorySize = context.getChatOption().getChatHistorySize();
		var reply = new StringBuffer();

		var contents = chatClient
				.prompt()
				.advisors(advisors)
				.messages(conversation.createPromptMessages())
				.messages(chatHistory.restorePromptMessages(maxHistorySize))
				.messages(userMessage.createPromptMessages())
				.tools(ToolCallbacks.from(tools.toArray()))
				.stream()
				.chatResponse()
				.buffer(CHAT_RESPONSE_BUFFER_SIZE)
				.map(ChatResponsesUtils::getTextContent)
				.filter(StringUtils::isNotBlank)
				.doOnNext(reply::append)
				.map(ChatMessageContent::of)
				.doOnComplete(() -> {
					var replyMessage = userMessage.reply(reply.toString());
					chatHistory.add(userMessage);
					chatHistory.add(replyMessage);
					chatManager.saveChatHistory(chatHistory);
					chatHistory.flush();
				});

		return ChatReply.builder()
				.contents(contents)
				.message(Mono.defer(() -> Mono.just(chatHistory.getLast())))
				.build();
	}


	private List<Advisor> getAdvisors(ChatContext context) {
		return chatAdvisorSuppliers
				.stream()
				.filter(chatAdvisorSupplier -> chatAdvisorSupplier.support(context))
				.map(chatAdvisorSupplier -> chatAdvisorSupplier.getAdvisor(context))
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

	private List<ChatTool> getTools(ChatContext context) throws ChatException {
		var tools = chatToolSuppliers
				.stream()
				.filter(supplier -> supplier.support(context))
				.map(supplier -> supplier.getTool(context))
				.toList();

		if (tools.isEmpty()) {
			return tools;
		}
		var toolDescription = tools.stream()
				.map(chatTool -> String.format("- %s: %s", chatTool.getName(), chatTool.getDescription()))
				.collect(Collectors.joining("\n"));
		var systemPrompt = "You will determine what tools to use based on the user's problem."
				+ "Please directly reply the tool names with delimiters ',', for example: tool1, tool2 ."
				+ "The tools are: \n"
				+ toolDescription;

		var toolsDecision = getChatClient(context)
				.prompt()
				.options(ChatOptions.builder()
						.model(CHAT_TOOLS_CHOSEN_MODEL)
						.build())
				.system(systemPrompt)
				.user(context.getUserMessage().tractContent())
				.call()
				.content();

		if (StringUtils.isBlank(toolsDecision)) {
			return new ArrayList<>();
		}

		var chosen = Arrays.asList(toolsDecision.split(","));
		log.info("tools chosen: {}", chosen);

		tools = tools.stream()
				.filter(chatTool -> chosen.contains(chatTool.getName()))
				.toList();

		return tools;
	}


}
