package com.rakuten.ross.aurora.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatMessageContent {

	private String text;

	public static ChatMessageContent of(String text) {
		return new ChatMessageContent().setText(text);
	}

	public ChatMessageContent merge(ChatMessageContent content) {
		return ChatMessageContent.of(this.text + content.text);
	}
}
