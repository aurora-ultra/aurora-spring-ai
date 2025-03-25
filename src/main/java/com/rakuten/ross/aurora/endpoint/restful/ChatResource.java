package com.rakuten.ross.aurora.endpoint.restful;

import com.rakuten.ross.aurora.application.ChatService;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.application.command.ChatOption;
import com.rakuten.ross.aurora.application.comvertor.ChatConvertor;
import com.rakuten.ross.aurora.application.vo.ChatMessageVo;
import com.rakuten.ross.aurora.endpoint.request.ChatRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "chat")
@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatResource {

	private final ChatService chatService;
	private final ChatConvertor chatConvertor;

	@PostMapping(path = "/{conversionId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ChatMessageVo chat(@PathVariable("conversionId") String conversionId,
							  @RequestBody ChatRequest request) {

		var command = ChatCommand.builder()
				.conversationId(conversionId)
				.contents(chatConvertor.toModel(request.getMessages()))
				.option(ChatOption.builder()
						.search(request.isSearch())
						.build())
				.build();

		var reply = chatService.chat(command);

		return chatConvertor.toVo(reply);

	}

}
