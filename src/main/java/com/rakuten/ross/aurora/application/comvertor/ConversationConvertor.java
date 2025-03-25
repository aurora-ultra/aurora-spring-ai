package com.rakuten.ross.aurora.application.comvertor;

import com.rakuten.ross.aurora.application.vo.ConversationVo;
import com.rakuten.ross.aurora.domain.Conversation;
import org.springframework.stereotype.Component;

@Component
public class ConversationConvertor {

	public ConversationVo toDto(Conversation conversation) {
		return new ConversationVo()
				.setId(conversation.getId())
				.setStartTime(conversation.getStartTime());
	}
}
