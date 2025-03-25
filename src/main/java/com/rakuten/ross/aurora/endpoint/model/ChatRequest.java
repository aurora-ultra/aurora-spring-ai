package com.rakuten.ross.aurora.endpoint.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChatRequest {

	private boolean search=false;

	@Valid
	@NotEmpty
	private String userInput;

}
