package com.rakuten.ross.aurora.domain.support;

import java.util.HashMap;
import java.util.Map;
import com.rakuten.ross.aurora.domain.model.Conversation;
import com.rakuten.ross.aurora.domain.ConversationRepository;
import org.springframework.stereotype.Repository;


@Repository
public class InMemoryConversationRepository implements ConversationRepository {
	private final Map<String, Conversation> store = new HashMap<>();

	@Override
	public Conversation findById(String conversationId) {
		return store.get(conversationId);
	}

	@Override
	public void save(Conversation conversation) {
		store.put(conversation.getId(), conversation);
	}
}
