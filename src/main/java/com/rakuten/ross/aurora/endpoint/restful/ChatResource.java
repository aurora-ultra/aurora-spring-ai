package com.rakuten.ross.aurora.endpoint.restful;

import com.rakuten.ross.aurora.application.ChatException;
import com.rakuten.ross.aurora.application.ChatOption;
import com.rakuten.ross.aurora.application.ChatReply;
import com.rakuten.ross.aurora.application.ChatService;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.endpoint.request.ChatRequest;
import com.rakuten.ross.aurora.endpoint.request.ChatResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "chat")
@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatResource {

	private final ChatService chatService;

	@PostMapping(path = "/{conversionId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ChatResponse> chat(@PathVariable("conversionId") String conversionId, @RequestBody @Validated ChatRequest request) throws ChatException {
		var command = ChatCommand.builder()
				.conversationId(conversionId)
				.content(request.getUserInput())
				.option(ChatOption.builder()
						.enableInternalSearch(false)
						.enableExternalSearch(false)
						.enableExampleTools(false)
						.enableMemory(true)
						.retrieveTopK(3)
						.build())
				.build();
		return chatService.chat(command)
				.getContents()
				.map(this::toResponse);
	}

	private ChatResponse toResponse(ChatReply.Content content) {
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setContent(content.getText());
		return chatResponse;
	}

}
