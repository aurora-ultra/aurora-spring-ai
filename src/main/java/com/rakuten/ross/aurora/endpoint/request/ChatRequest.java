package com.rakuten.ross.aurora.endpoint.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChatRequest {

    @Schema(example = "hello, Aurora!", defaultValue = "Hi, there")
    private String message = "Hi, there";

}
