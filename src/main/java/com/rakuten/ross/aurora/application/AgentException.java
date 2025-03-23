package com.rakuten.ross.aurora.application;

public class AgentException extends RuntimeException {
    private AgentException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AgentException of(String message, Throwable cause) {
        return new AgentException(message, cause);
    }
}
