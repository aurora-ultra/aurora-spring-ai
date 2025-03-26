package com.rakuten.ross.aurora.domain;

import java.util.ArrayList;
import java.util.List;
import com.rakuten.ross.aurora.core.layer.DomainModel;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.ai.chat.messages.Message;

@Getter
public final class ChatHistory implements DomainModel {

	private final List<ChatMessage> oldMessages;
	private final List<ChatMessage> newMessages;

	private ChatHistory(List<ChatMessage> messages) {
		this.oldMessages = messages;
		this.newMessages = new ArrayList<>();
	}

	static ChatHistory of(List<ChatMessage> messages) {
		return new ChatHistory(messages);
	}

	public void add(ChatMessage message) {
		newMessages.add(message);
	}

	public void flush() {
		oldMessages.addAll(newMessages);
	}

	public ChatMessage getLast() {
		if (CollectionUtils.isNotEmpty(this.getNewMessages())) {
			return this.getNewMessages().get(this.getNewMessages().size() - 1);
		}
		return this.getOldMessages().get(this.getNewMessages().size() - 1);
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
