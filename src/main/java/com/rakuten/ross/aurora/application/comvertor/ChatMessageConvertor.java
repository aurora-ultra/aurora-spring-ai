package com.rakuten.ross.aurora.application.comvertor;

import java.util.List;
import com.rakuten.ross.aurora.application.dto.ChatMessageContentDto;
import com.rakuten.ross.aurora.application.dto.ChatMessageDto;
import com.rakuten.ross.aurora.domain.model.ChatMessage;
import com.rakuten.ross.aurora.domain.model.ChatMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageConvertor {

	public ChatMessageDto toDto(ChatMessage message) {
		return new ChatMessageDto()
				.setMessageId(message.getMessageId())
				.setConversationId(message.getConversationId())
				.setSendTime(message.getSendTime())
				.setReplyMessageId(message.getReplyMessageId())
				.setConversationId(message.getConversationId())
				.setMessageType(message.getMessageType())
				.setContent(this.toDto(message.getContent()))
				;
	}

	public ChatMessageContentDto toDto(ChatMessageContent content) {
		return new ChatMessageContentDto()
				.setText(content.getText())
				;
	}

	public List<ChatMessageContentDto> toDto(List<ChatMessageContent> contents) {
		return contents.stream()
				.map(this::toDto)
				.toList()
				;
	}
}
