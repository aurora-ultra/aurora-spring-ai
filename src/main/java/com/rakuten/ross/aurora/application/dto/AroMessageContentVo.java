package com.rakuten.ross.aurora.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "MessageContent")
public class AroMessageContentVo {

    private String text;

}
