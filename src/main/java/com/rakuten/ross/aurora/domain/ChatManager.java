package com.rakuten.ross.aurora.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatManager {

	private final ChatMessageRepository chatMessageRepository;
	private final ConversationRepository conversationRepository;

	public Conversation getOrCreateConversation(String conversationId) {
		Conversation conversation = new Conversation();
		conversation.setId(conversationId);
		conversation.setStartTime(LocalDateTime.now());
		conversation.setAgent(this.getAgent("0001"));
		return conversation;
	}

	public Agent getAgent(String agentId) {
		Agent agent = new Agent();
		agent.setAgentId(agentId);
		agent.setName("aurora");
		agent.setPrompt("你叫钢蛋儿，来自蒙塔基，是一名聪明的AI助手, 你喜欢不断的跟人说：你好，我叫钢蛋儿，我来自蒙塔基");
		agent.setOwnerId("system");
		agent.setMemorySize(5);
		return agent;
	}

	public List<ChatMessage> getChatHistory(String conversionId, long size) {
		return chatMessageRepository.listByConversation(conversionId);
	}

	public void saveChatHistory(List<ChatMessage> messages) {
		for (ChatMessage message : messages) {
			log.info("save chat history: {}", message.tractContent());
			chatMessageRepository.save(message);
		}
	}
}
