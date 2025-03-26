package com.rakuten.ross.aurora.endpoint.model;

import lombok.Data;

@Data
public class ChatResponse {

	private Type type;

	private String content;

	private String messageId;

	private String replyMessageId;

	public enum Type {
		text,
		meta,
	}

}
