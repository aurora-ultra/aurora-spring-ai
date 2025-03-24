package com.rakuten.ross.aurora.endpoint.request;

import com.rakuten.ross.aurora.application.dto.AroMessageContentVo;
import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    private List<AroMessageContentVo> messages;
}
