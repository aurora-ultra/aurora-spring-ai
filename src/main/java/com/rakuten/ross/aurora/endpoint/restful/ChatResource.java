package com.rakuten.ross.aurora.endpoint.restful;

import com.rakuten.ross.aurora.application.ChatException;
import com.rakuten.ross.aurora.application.ChatOption;
import com.rakuten.ross.aurora.application.ChatService;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.domain.ChatMessage;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
import com.rakuten.ross.aurora.endpoint.model.ChatRequest;
import com.rakuten.ross.aurora.endpoint.model.ChatResponse;
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
						.retrieve(request.isRetrieve())
						.search(request.isSearch())
						.retrieveTopK(3)
						.chatHistorySize(5)
						.build())
				.build();
		var reply = chatService.chat(command);
		var contents = reply.getContents().map(this::toResponse);
		var message = reply.getMessage().map(this::toResponse);
		return contents.concatWith(message);
	}

	private ChatResponse toResponse(ChatMessage chatMessage) {
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setMessageId(chatMessage.getMessageId());
		chatResponse.setReplyMessageId(chatMessage.getReplyMessageId());
		chatResponse.setType(ChatResponse.Type.meta);
		return chatResponse;
	}

	private ChatResponse toResponse(ChatMessageContent chatMessage) {
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setContent(chatMessage.getText());
		chatResponse.setType(ChatResponse.Type.text);
		return chatResponse;
	}
}
