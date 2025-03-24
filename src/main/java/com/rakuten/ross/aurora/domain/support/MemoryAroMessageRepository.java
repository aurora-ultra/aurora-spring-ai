package com.rakuten.ross.aurora.domain.support;

import com.rakuten.ross.aurora.domain.AroMessage;
import com.rakuten.ross.aurora.domain.AroMessageRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryAroMessageRepository implements AroMessageRepository {

    private final Map<String,List<AroMessage>> store = new HashMap<>();

    @Override
    public void save(AroMessage message) {
        store.computeIfAbsent(message.getConversationId(), k -> new ArrayList<>()).add(message);
    }

    @Override
    public List<AroMessage> listByConversation(String conversationId) {
        return store.computeIfAbsent(conversationId, k -> new ArrayList<>());
    }
}
