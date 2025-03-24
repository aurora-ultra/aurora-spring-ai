package com.rakuten.ross.aurora.application.dto;

import com.rakuten.ross.aurora.domain.AroMessage;
import com.rakuten.ross.aurora.domain.AroMessageContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(name = "Room")
@Accessors(chain = true)
public class AroMessageDto {

    private String messageId;

    private String replyMessageId;

    private LocalDateTime sendTime;

    private AroMessage.Type messageType;

    private String conversationId;

    private List<AroMessageContent> content;
    
    
}
