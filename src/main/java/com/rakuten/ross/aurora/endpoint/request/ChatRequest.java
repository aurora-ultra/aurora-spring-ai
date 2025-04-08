package com.rakuten.ross.aurora.endpoint.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChatRequest {

	@Valid
	@NotEmpty
	private String userInput;

}
