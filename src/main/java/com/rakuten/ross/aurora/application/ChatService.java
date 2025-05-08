package com.rakuten.ross.aurora.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.domain.ChatManager;
import com.rakuten.ross.aurora.domain.Conversation;
import com.rakuten.ross.aurora.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
	public static final int CHAT_RESPONSE_BUFFER_SIZE = 24;
	public static final String CHAT_TOOLS_CHOSEN_MODEL = "gpt-3.5-turbo";

	private final ChatManager chatManager;

	private final List<ChatToolSupplier<?>> chatToolSuppliers;
	private final List<ChatClientSupplier> chatClientSuppliers;
	private final List<ChatAdvisorSupplier> chatAdvisorSuppliers;

	public ChatReply chat(ChatCommand command) throws ChatException {
		try {
			var user = User.mock();
			var chatOption = command.getOption();
			var conversation = getConversation(command.getConversationId());
			var userMessage = createUserMessage(command);
			var context = ChatContext.builder()
					.user(user)
					.userMessage(userMessage)
					.chatOption(chatOption)
					.conversation(conversation)
					.build();
			return this.chat(context);
		} catch (Exception e) {
			throw ChatException.of("Something wrong when processing the chat command", e);
		}
	}

	private ChatReply chat(ChatContext context) throws ChatException {
		var tools = getTools(context);
		var advisors = getAdvisors(context);
		var chatClient = getChatClient(context);
		var conversation = context.getConversation();
		var userMessage = context.getUserMessage();

		var contents = chatClient
				.prompt()
				.advisors(advisors)
				.messages(conversation.createPromptMessages())
				.messages(userMessage)
				.toolCallbacks(ToolCallbacks.from(tools.toArray()))
				.toolContext(context.getAttributes())
				.stream()
				.content()
				.buffer(CHAT_RESPONSE_BUFFER_SIZE)
				.map(strings -> String.join("", strings));

		return ChatReply.builder()
				.contents(contents)
				.build();
	}

	private UserMessage createUserMessage(ChatCommand command) {
		return new UserMessage(command.getContent());
	}

	private Conversation getConversation(String conversationId) {
		return chatManager.getOrCreateConversation(conversationId);
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

	private List<Object> getTools(ChatContext context) throws ChatException {
		var toolSuppliers = chatToolSuppliers.stream()
				.filter(supplier -> supplier.support(context))
				.map(chatTool -> String.format("- %s: %s", chatTool.getName(), chatTool.getDescription()))
				.toList();

		var systemPrompt = "You will determine what tools to use based on the user's problem." +
				"Please directly reply the tool names with delimiters ',' and reply empty if no tools is usable " +
				"Reply example: tool1,tool2." +
				"The tools are: \n" +
				String.join(",", toolSuppliers);

		var toolsDecision = getChatClient(context)
				.prompt()
				.options(ChatOptions.builder()
						.model(CHAT_TOOLS_CHOSEN_MODEL)
						.build())
				.system(systemPrompt)
				.messages(context.getUserMessage())
				.call()
				.content();

		if (StringUtils.isBlank(toolsDecision)) {
			return new ArrayList<>();
		}

		var chosen = Arrays.asList(toolsDecision.split(","));

		var tools = chatToolSuppliers
				.stream()
				.filter(supplier -> supplier.support(context))
				.filter(supplier -> chosen.contains(supplier.getName()))
				.map(supplier -> supplier.getTool(context))
				.map(o -> (Object) o)
				.collect(Collectors.toList());

		log.info("tools chosen: {}", chosen);

		return tools;
	}
}
