package com.rakuten.ross.aurora.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatManager {

	private final ChatMessageRepository chatMessageRepository;
	private final ConversationRepository conversationRepository;

	public void saveConversation(Conversation conversation) {
		conversationRepository.save(conversation);
	}

	public Conversation getConversation(String conversationId) {
		return conversationRepository.findById(conversationId);
	}

	public ChatHistory getHistory(String conversionId) {
		var messageHistories = chatMessageRepository.listByConversation(conversionId);
		return ChatHistory.of(messageHistories);
	}

	public void saveMessage(ChatMessage... message) {
		for (ChatMessage chatMessage : message) {
			chatMessageRepository.save(chatMessage);
		}
	}
}
