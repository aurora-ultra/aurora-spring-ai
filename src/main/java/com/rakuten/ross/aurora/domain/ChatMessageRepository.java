package com.rakuten.ross.aurora.domain;

import java.util.List;
import com.rakuten.ross.aurora.domain.model.ChatMessage;

public interface ChatMessageRepository {
	void save(ChatMessage message);

	List<ChatMessage> listByConversation(String conversationId);
}
