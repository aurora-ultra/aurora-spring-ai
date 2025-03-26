package com.rakuten.ross.aurora.application.tools;

public class DocumentToolsException extends RuntimeException {
	private DocumentToolsException(String message, Throwable cause) {
		super(message, cause);
	}

	public static DocumentToolsException of(String message, Throwable cause) {
		return new DocumentToolsException(message, cause);
	}


}
