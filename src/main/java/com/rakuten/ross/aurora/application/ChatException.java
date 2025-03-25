package com.rakuten.ross.aurora.application;

public class ChatException extends RuntimeException {

	private ChatException(String message) {
		super(message);
	}
	private ChatException(String message, Throwable cause) {
		super(message, cause);
	}



	public static ChatException of(String message, Throwable cause) {
		return new ChatException(message, cause);
	}

	public static ChatException of(String message) {
		return new ChatException(message);
	}
}
