package com.rakuten.ross.aurora.endpoint.comvertor;

import java.util.List;
import com.rakuten.ross.aurora.domain.ChatMessage;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
import com.rakuten.ross.aurora.endpoint.dto.ChatMessageContentDto;
import com.rakuten.ross.aurora.endpoint.dto.ChatMessageDto;
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
				.setMessageRole(message.getRole())
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
