package com.rakuten.ross.aurora.domain;

import java.util.List;

public interface AroMessageRepository {
    void save(AroMessage message);

    List<AroMessage> listByConversation(String conversationId);
}
