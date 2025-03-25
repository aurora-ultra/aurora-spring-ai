package com.rakuten.ross.aurora.domain;

import java.util.List;

public interface ChatMessageRepository {
	void save(ChatMessage message);

	List<ChatMessage> listByConversation(String conversationId);
}
