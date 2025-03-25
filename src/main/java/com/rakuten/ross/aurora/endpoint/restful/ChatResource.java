package com.rakuten.ross.aurora.endpoint.restful;

import com.rakuten.ross.aurora.application.ChatService;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.application.command.ChatOption;
import com.rakuten.ross.aurora.application.comvertor.ChatMessageConvertor;
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
	public Flux<Object> chat(@PathVariable("conversionId") String conversionId, @RequestBody @Validated ChatRequest request) {
		var command = ChatCommand.builder()
				.conversationId(conversionId)
				.content(request.getUserInput())
				.option(ChatOption.builder()
						.search(request.isSearch())
						.build())
				.build();

		var reply = chatService.chat(command);

		var flux = reply.getContents().map(chatMessage -> {
			ChatResponse chatResponse = new ChatResponse();
			chatResponse.setContent(chatMessage.getText());
			chatResponse.setType(ChatResponse.Type.text);
			return chatResponse;
		});

		return Flux.concat(flux, Flux.defer(() -> {
			var last = reply.getContext().getChatHistory().getLast();
			ChatResponse chatResponse = new ChatResponse();
			chatResponse.setMessageId(last.getMessageId());
			chatResponse.setReplyMessageId(last.getReplyMessageId());
			chatResponse.setType(ChatResponse.Type.meta);
			return Flux.just(chatResponse);
		}));

	}


}
