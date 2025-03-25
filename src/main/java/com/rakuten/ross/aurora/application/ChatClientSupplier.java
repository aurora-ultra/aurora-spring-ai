package com.rakuten.ross.aurora.application;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;

public interface ChatClientSupplier {
	boolean support(ChatContext context);

	ChatClient getChatClient(ChatContext context);
}
