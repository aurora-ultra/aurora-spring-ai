package com.rakuten.ross.aurora.endpoint.restful;

import com.rakuten.ross.aurora.application.ChatService;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.application.command.ChatOption;
import com.rakuten.ross.aurora.application.comvertor.ChatMessageConvertor;
import com.rakuten.ross.aurora.application.dto.ChatMessageContentDto;
import com.rakuten.ross.aurora.endpoint.model.ChatRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
	private final ChatMessageConvertor chatMessageConvertor;

	@PostMapping(path = "/{conversionId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ChatMessageContentDto> chat(@PathVariable("conversionId") String conversionId, @RequestBody @Validated ChatRequest request) {
		var command = ChatCommand.builder()
				.conversationId(conversionId)
				.content(request.getUserInput())
				.option(ChatOption.builder()
						.search(request.isSearch())
						.build())
				.build();
		return chatService.chat(command)
				.map(chatMessageConvertor::toDto);
	}

	@PostMapping(path = "/{conversionId}.test")
	public Flux<ChatMessageContentDto> chatSync(@PathVariable("conversionId") String conversionId, @RequestBody @Valid ChatRequest request) {
		return chat(conversionId, request);
	}

}
