package com.rakuten.ross.aurora.application.comvertor;

import com.rakuten.ross.aurora.application.dto.ConversationDto;
import com.rakuten.ross.aurora.domain.model.Conversation;
import org.springframework.stereotype.Component;

@Component
public class ConversationConvertor {

	public ConversationDto toVo(Conversation conversation) {
		return new ConversationDto()
				.setId(conversation.getId())
				.setStartTime(conversation.getStartTime());
	}
}
