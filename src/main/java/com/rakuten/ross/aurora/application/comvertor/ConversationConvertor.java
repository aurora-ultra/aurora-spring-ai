package com.rakuten.ross.aurora.application.comvertor;

import com.rakuten.ross.aurora.domain.Conversation;
import com.rakuten.ross.aurora.application.model.ConversationDto;
import org.springframework.stereotype.Component;

@Component
public class ConversationConvertor {

	public ConversationDto toDto(Conversation conversation) {
		return new ConversationDto()
				.setId(conversation.getId())
				.setStartTime(conversation.getStartTime());
	}
}
