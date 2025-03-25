package com.rakuten.ross.aurora.endpoint.request;

import java.util.List;
import com.rakuten.ross.aurora.application.vo.ChatMessageContentVo;
import lombok.Data;

@Data
public class ChatRequest {
	private boolean search;
	private List<ChatMessageContentVo> messages;
}
