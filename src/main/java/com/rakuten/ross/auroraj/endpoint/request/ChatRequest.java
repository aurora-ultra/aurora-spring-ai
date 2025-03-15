package com.rakuten.ross.auroraj.endpoint.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChatRequest {

    @Schema(example = "你好，aurora小组", defaultValue = "Hi, Aurora!")
    private String input = "安德鲁，周树人，周杰伦，他们三个人谁更广为人知？";

}
