package com.rakuten.ross.aurora.domain.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.rakuten.ross.aurora.domain.model.ChatMessage;
import com.rakuten.ross.aurora.domain.ChatMessageRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryChatMessageRepository implements ChatMessageRepository {

	private final Map<String, List<ChatMessage>> store = new HashMap<>();

	@Override
	public void save(ChatMessage message) {
		store.computeIfAbsent(message.getConversationId(), k -> new ArrayList<>()).add(message);
	}

	@Override
	public List<ChatMessage> listByConversation(String conversationId) {
		return store.computeIfAbsent(conversationId, k -> new ArrayList<>());
	}
}
