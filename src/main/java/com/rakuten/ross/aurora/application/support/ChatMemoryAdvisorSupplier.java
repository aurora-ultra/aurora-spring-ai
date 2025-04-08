package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatAdvisorSupplier;
import com.rakuten.ross.aurora.application.ChatContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMemoryAdvisorSupplier implements ChatAdvisorSupplier {

	private final ChatMemory chatMemory;

	@Override
	public boolean support(ChatContext context) {
		return context.getChatOption().isEnableMemory() &&
				context.getConversation().getMemorySize() > 0;
	}

	@Override
	public Advisor getAdvisor(ChatContext context) {
		return MessageChatMemoryAdvisor
				.builder(chatMemory)
				.conversationId(context.getConversation().getId())
				.chatMemoryRetrieveSize(context.getConversation().getMemorySize())
				.build();
	}
}
