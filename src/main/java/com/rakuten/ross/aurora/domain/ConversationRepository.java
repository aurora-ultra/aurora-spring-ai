package com.rakuten.ross.aurora.domain;

import com.rakuten.ross.aurora.domain.model.Conversation;

public interface ConversationRepository {

	Conversation findById(String conversationId);

	void save(Conversation conversation);

}
