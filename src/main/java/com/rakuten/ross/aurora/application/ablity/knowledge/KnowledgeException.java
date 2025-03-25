package com.rakuten.ross.aurora.application.ablity.knowledge;

public class KnowledgeException extends RuntimeException {

    private KnowledgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public static KnowledgeException of(String message, Throwable cause) {
        return new KnowledgeException(message, cause);
    }

}
