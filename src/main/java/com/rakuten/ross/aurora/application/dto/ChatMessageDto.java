package com.rakuten.ross.aurora.application.dto;

import com.rakuten.ross.aurora.core.layer.Dto;
import com.rakuten.ross.aurora.domain.model.ChatMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(name = "ChatMessage")
@Accessors(chain = true)
public class ChatMessageDto implements Dto {

    private String messageId;

    private String replyMessageId;

    private LocalDateTime sendTime;

    private ChatMessage.Type messageType;

    private String conversationId;

    private List<ChatMessageContentDto> content;

}
