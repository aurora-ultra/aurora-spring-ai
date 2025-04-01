package com.rakuten.ross.aurora.domain;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatManager {

	private final ChatMessageRepository chatMessageRepository;
	private final ConversationRepository conversationRepository;

	public void saveConversation(Conversation conversation) {
		conversationRepository.save(conversation);
	}

	public Conversation getOrCreateConversation(String conversationId) {
		Conversation conversation = new Conversation();
		conversation.setId(conversationId);
		Agent agent = new Agent();
		agent.setName("aurora");
		agent.setPrompt("你叫钢蛋儿，来自蒙塔基，是一名聪明的AI助手。");
		agent.setOwnerId("system");
		agent.setAgentId("00001");
		conversation.setAgent(agent);
		return conversation;
	}

	public ChatHistory getChatHistory(String conversionId) {
		var messageHistories = chatMessageRepository.listByConversation(conversionId);
		return ChatHistory.of(messageHistories);
	}

	public void saveChatHistory(ChatHistory chatHistory) {
		for (ChatMessage newMessage : chatHistory.getNewMessages()) {
			log.info("save chat history: {}", newMessage.tractContent());
			chatMessageRepository.save(newMessage);
		}
	}
}
