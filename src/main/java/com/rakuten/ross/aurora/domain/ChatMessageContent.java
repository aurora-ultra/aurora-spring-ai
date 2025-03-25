package com.rakuten.ross.aurora.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageContent {

	private String text;

	public static ChatMessageContent empty() {
		return new ChatMessageContent();
	}

	public static ChatMessageContent of(String text) {
		ChatMessageContent content = new ChatMessageContent();
		content.setText(text);
		return content;
	}

}
