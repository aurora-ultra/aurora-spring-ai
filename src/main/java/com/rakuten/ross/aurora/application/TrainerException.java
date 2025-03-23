package com.rakuten.ross.aurora.application;

public class TrainerException extends RuntimeException {
    private TrainerException(String message, Throwable cause) {
        super(message, cause);
    }

    public static TrainerException of(String message, Throwable cause) {
        return new TrainerException(message, cause);
    }
}
