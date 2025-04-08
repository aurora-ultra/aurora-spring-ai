package com.rakuten.ross.aurora.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.rakuten.ross.aurora.domain.ChatMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.ai.chat.messages.Message;

@Slf4j
@Getter
public final class ChatHistory {

	private final List<ChatMessage> oldMessages;
	private final List<ChatMessage> newMessages;

	private ChatHistory(List<ChatMessage> messages) {
		this.oldMessages = messages;
		this.newMessages = new ArrayList<>();
	}

	public static ChatHistory of(List<ChatMessage> messages) {
		return new ChatHistory(messages);
	}

	public void add(ChatMessage message) {
		newMessages.add(message);
	}

	public void flush() {
		oldMessages.addAll(newMessages);
		newMessages.clear();
	}

	public ChatMessage getLast() {
		var messages = Optional.ofNullable(this.getNewMessages())
				.filter(CollectionUtils::isNotEmpty)
				.orElseGet(this::getOldMessages);
		return messages.get(messages.size() - 1);
	}

	public List<Message> restorePromptMessages(int keepSize) {
		var messages = getOldMessages();

		messages = messages.subList(Math.max(0, messages.size() - keepSize), messages.size());

		return messages
				.stream()
				.flatMap(aroMessage -> aroMessage.createPromptMessages().stream())
				.toList();
	}


}
