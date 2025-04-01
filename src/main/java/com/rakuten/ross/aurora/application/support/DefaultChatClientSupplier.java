package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatClientSupplier;
import com.rakuten.ross.aurora.application.ChatContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.lang.Contract;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultChatClientSupplier implements ChatClientSupplier {
	private final ChatModel chatModel;

	@Override
	public boolean support(ChatContext context) {
		return true;
	}

	@Override
	public ChatClient getChatClient(ChatContext context) {
		return ChatClient.builder(chatModel)
				.build();
	}
}
