package com.rakuten.ross.aurora.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.application.command.ChatOption;
import com.rakuten.ross.aurora.application.command.ConversationStartCommand;
import com.rakuten.ross.aurora.core.support.TimeProvider;
import com.rakuten.ross.aurora.domain.ChatManager;
import com.rakuten.ross.aurora.domain.ChatMessage;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
import com.rakuten.ross.aurora.domain.Conversation;
import com.rakuten.ross.aurora.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatModel chatModel;
	private final TimeProvider timeProvider;
	private final ChatManager chatManager;
	private final VectorStore vectorStore;

	public Conversation startConversation(ConversationStartCommand command) {
		var conversation = Conversation.start(UUID.randomUUID().toString(), timeProvider.now());
		chatManager.saveConversation(conversation);
		return conversation;
	}

	// todo stream reply
	public ChatMessage chat(ChatCommand command) {
		// when		who		what			where			how
		// -------------------------------------------------------------
		// now		user 	userMessage		conversation	chatOption

		var now = timeProvider.now();

		var userMessage = ChatMessage.fromUser(
				UUID.randomUUID().toString(),
				command.getConversationId(),
				now,
				command.getContents()
		);

		var user = User.mock();

		var chatOption = command.getOption();

		var conversation = chatManager.getConversation(command.getConversationId());

		// todo can we use spring to implement the history or replace with summary?
		var chatHistory = chatManager.getHistory(command.getConversationId());

		var advisors = getAdvisors(chatOption);

		var reply = creatChatClient()
				.prompt()
				.advisors(advisors)
				.messages(chatHistory.toMessages(5))
				.messages(userMessage.toCompletionMessages())
				.call()
				.content();

		var assistantMessage = ChatMessage.fromAssistant(
				userMessage,
				now,
				List.of(ChatMessageContent.of(reply))
		);

		chatManager.saveMessage(userMessage, assistantMessage);

		return assistantMessage;
	}

	private ArrayList<Advisor> getAdvisors(ChatOption option) {
		var advisors = new ArrayList<Advisor>();
		if (option.isSearch()) {
			advisors.add(getQuestionAnswerAdvisor());
		}
		return advisors;
	}

	private Advisor getQuestionAnswerAdvisor() {
		return new QuestionAnswerAdvisor(
				vectorStore,
				SearchRequest.builder()
						.topK(2)
						.build(),
				"""
						上下文信息如下，用 --------------------- 包围
						
						---------------------
						{question_answer_context}
						---------------------
						
						根据上下文和提供的历史信息（而非先验知识）回复用户评论。如果答案不在上下文中，请告知用户你无法回答该问题。
						"""
		);
	}

	private ChatClient creatChatClient() {
		return ChatClient.builder(chatModel)
				.build();
	}


}
