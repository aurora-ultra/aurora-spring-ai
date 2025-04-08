package com.rakuten.ross.aurora.endpoint.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.rakuten.ross.aurora.core.layer.Dto;
import com.rakuten.ross.aurora.domain.ChatMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "ChatMessage")
@Accessors(chain = true)
public class ChatMessageDto implements Dto {

	@Schema(description = "The unique identifier of the message", example = "123456")
	private String messageId;

	@Schema(description = "The unique identifier of the message that this message is a reply to", example = "123456")
	private String replyMessageId;

	@Schema(description = "The time the message was sent", example = "2021-01-01T00:00:00")
	private LocalDateTime sendTime;

	@Schema(description = "The type of the message", example = "User")
	private ChatMessage.Role messageRole;

	@Schema(description = "The unique identifier of the conversation", example = "123456")
	private String conversationId;

	@Schema(description = "The content of the message")
	private List<ChatMessageContentDto> content;

}
