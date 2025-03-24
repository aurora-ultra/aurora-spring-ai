package com.rakuten.ross.aurora.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AroChatManager {

    private final AroMessageRepository aroMessageRepository;

    public AroChatHistory getHistory(String conversionId) {
        var messageHistories = aroMessageRepository.listByConversation(conversionId);
        return AroChatHistory.of(messageHistories);
    }

    public Conversation createConversation() {
        return new Conversation(UUID.randomUUID().toString());
    }

    public void saveMessage(AroMessage... message) {
        for (AroMessage aroMessage : message) {
            aroMessageRepository.save(aroMessage);
        }
    }
}
