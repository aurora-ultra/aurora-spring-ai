package com.rakuten.ross.aurora.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AroChatManager {

    private final AroMessageRepository aroMessageRepository;

    public AroChatHistory getHistory(String conversionId) {
        var messageHistories = aroMessageRepository.listByConversation(conversionId);
        return AroChatHistory.of(messageHistories);
    }

    public void saveMessage(AroMessage message) {
        aroMessageRepository.save(message);
    }
}
