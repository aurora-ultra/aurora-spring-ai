package com.rakuten.ross.aurora.application.comvertor;

import java.util.List;
import com.rakuten.ross.aurora.application.vo.ChatMessageContentVo;
import com.rakuten.ross.aurora.application.vo.ChatMessageVo;
import com.rakuten.ross.aurora.domain.ChatMessage;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageConvertor {

	public ChatMessageVo toVo(ChatMessage message) {
		return new ChatMessageVo()
				.setMessageId(message.getMessageId())
				.setConversationId(message.getConversationId())
				.setSendTime(message.getSendTime())
				.setReplyMessageId(message.getReplyMessageId())
				.setConversationId(message.getConversationId())
				.setMessageType(message.getMessageType())
				.setContent(this.toVo(message.getContent()))
				;
	}

	public ChatMessageContentVo toVo(ChatMessageContent content) {
		return new ChatMessageContentVo()
				.setText(content.getText())
				;
	}

	public List<ChatMessageContentVo> toVo(List<ChatMessageContent> contents) {
		return contents.stream()
				.map(this::toVo)
				.toList()
				;
	}
}
