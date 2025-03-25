package com.rakuten.ross.aurora.application.vo;

import java.time.LocalDateTime;
import com.rakuten.ross.aurora.core.layer.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "Conversation")
@Accessors(chain = true)
public class ConversationDto implements Dto {

	private String id;

	private LocalDateTime startTime;
}
