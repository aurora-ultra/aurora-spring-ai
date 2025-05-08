package com.rakuten.ross.aurora.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.messages.Message;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conversation implements PromptMessageProvider {

	private String id;

	private LocalDateTime startTime;

	private Agent agent;

	public static Conversation of(String id, LocalDateTime startTime, Agent agent) {
		var conversation = new Conversation();
		conversation.setId(id);
		conversation.setStartTime(startTime);
		conversation.setAgent(agent);
		return conversation;
	}

	@Override
	public List<Message> createPromptMessages() {
		return agent.createPromptMessages();
	}

	public int getMemorySize() {
		return agent.getMemorySize();
	}
}
