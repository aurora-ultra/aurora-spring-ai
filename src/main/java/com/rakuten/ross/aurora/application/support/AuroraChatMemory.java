package com.rakuten.ross.aurora.application.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuroraChatMemory implements ChatMemory {

	private final Map<String, List<Message>> inMemoryMessageStore = new HashMap<>();

	@Override
	public void add(String conversationId, List<Message> messages) {
		getList(conversationId).addAll(messages);
	}

	@Override
	public List<Message> get(String conversationId, int lastN) {
		var list = getList(conversationId);
		list = list.subList(Math.max(0, list.size() - lastN), list.size());
		return new ArrayList<>(list);
	}

	@Override
	public void clear(String conversationId) {
		inMemoryMessageStore.remove(conversationId);
	}

	private List<Message> getList(String conversationId) {
		return inMemoryMessageStore.computeIfAbsent(conversationId, s -> new CopyOnWriteArrayList<>());
	}
}
