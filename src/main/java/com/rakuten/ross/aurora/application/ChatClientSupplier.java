package com.rakuten.ross.aurora.application;

import org.springframework.ai.chat.client.ChatClient;

public interface ChatClientSupplier {
	boolean support(ChatContext context);

	ChatClient getChatClient(ChatContext context);
}
