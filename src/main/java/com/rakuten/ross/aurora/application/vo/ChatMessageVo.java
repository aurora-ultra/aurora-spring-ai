package com.rakuten.ross.aurora.application.vo;

import com.rakuten.ross.aurora.core.layer.Dto;
import com.rakuten.ross.aurora.domain.ChatMessage;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
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
public class ChatMessageVo implements Dto {

    private String messageId;

    private String replyMessageId;

    private LocalDateTime sendTime;

    private ChatMessage.Type messageType;

    private String conversationId;

    private List<ChatMessageContent> content;

}
