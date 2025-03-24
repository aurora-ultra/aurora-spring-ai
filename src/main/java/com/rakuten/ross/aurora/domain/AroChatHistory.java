package com.rakuten.ross.aurora.domain;

import lombok.Getter;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

@Getter
public final class AroChatHistory {

    private final List<AroMessage> messages;

    private AroChatHistory(List<AroMessage> messages) {
        this.messages = messages;
    }

    public static AroChatHistory of(List<AroMessage> messages) {
        return new AroChatHistory(messages);
    }

    public List<Message> toMessages(int keepSize) {
        var messages = getMessages();

        messages = messages.subList(Math.max(0, messages.size() - keepSize), messages.size());

        return messages
            .stream()
            .flatMap(aroMessage -> aroMessage.toMessages().stream())
            .toList();
    }
}
