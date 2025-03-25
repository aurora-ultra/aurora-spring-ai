package com.rakuten.ross.aurora.application.comvertor;

import java.util.Collection;
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
public class ChatConvertor {

	public ChatMessageContent toModel(ChatMessageContentVo chatMessageContentVo) {
		return new ChatMessageContent()
				.setText(chatMessageContentVo.getText())
				;
	}

	public List<ChatMessageContent> toModel(Collection<ChatMessageContentVo> chatMessageContentVos) {
		return chatMessageContentVos.stream()
				.map(this::toModel)
				.toList()
				;
	}

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

	private ChatMessageContent toVo(ChatMessageContent content) {
		return new ChatMessageContent()
				.setText(content.getText())
				;
	}

	private List<ChatMessageContent> toVo(List<ChatMessageContent> contents) {
		return contents.stream()
				.map(this::toVo)
				.toList()
				;
	}
}
