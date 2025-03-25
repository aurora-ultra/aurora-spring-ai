package com.rakuten.ross.aurora.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import com.rakuten.ross.aurora.core.layer.DomainModel;
import com.rakuten.ross.aurora.domain.PromptMessageCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.messages.Message;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conversation implements DomainModel, PromptMessageCreator {

	private String id;

	private LocalDateTime startTime;

	private Agent agent;

	public static Conversation start(String id, LocalDateTime startTime) {
		var conversation = new Conversation();
		conversation.setId(id);
		conversation.setStartTime(startTime);
		return conversation;
	}

	@Override
	public List<Message> createPromptMessages() {
		return agent.createPromptMessages();
	}
}
