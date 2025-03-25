package com.rakuten.ross.aurora.domain;

import java.util.List;
import com.rakuten.ross.aurora.core.layer.Model;
import lombok.Getter;
import org.springframework.ai.chat.messages.Message;

@Getter
public final class ChatHistory implements Model {

	private final List<ChatMessage> messages;

	private ChatHistory(List<ChatMessage> messages) {
		this.messages = messages;
	}

	static ChatHistory of(List<ChatMessage> messages) {
		return new ChatHistory(messages);
	}

	public List<Message> toMessages(int keepSize) {
		var messages = getMessages();

		messages = messages.subList(Math.max(0, messages.size() - keepSize), messages.size());

		return messages
				.stream()
				.flatMap(aroMessage -> aroMessage.toCompletionMessages().stream())
				.toList();
	}
}
