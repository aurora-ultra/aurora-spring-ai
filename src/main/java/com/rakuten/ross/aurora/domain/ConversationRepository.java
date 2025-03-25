package com.rakuten.ross.aurora.domain;

public interface ConversationRepository {

	Conversation findById(String conversationId);

	void save(Conversation conversation);

}
