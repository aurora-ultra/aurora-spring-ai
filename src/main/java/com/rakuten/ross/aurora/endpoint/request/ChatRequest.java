package com.rakuten.ross.aurora.endpoint.request;

import java.util.List;
import com.rakuten.ross.aurora.application.vo.ChatMessageContentVo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChatRequest {

	private boolean search;

	@Valid
	@NotEmpty
	private String userInput;

}
