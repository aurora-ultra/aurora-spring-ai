package com.rakuten.ross.aurora.endpoint.restful;

import com.rakuten.ross.aurora.application.ChatService;
import com.rakuten.ross.aurora.application.command.ConversationStartCommand;
import com.rakuten.ross.aurora.application.comvertor.ConversationConvertor;
import com.rakuten.ross.aurora.application.model.ConversationDto;
import com.rakuten.ross.aurora.endpoint.request.ConversationStartRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "conversation")
@RequestMapping("/conversation")
@RestController
@RequiredArgsConstructor
public class ConversationResource {

	private final ChatService chatService;
	private final ConversationConvertor conversationConvertor;

	@PostMapping
	public ConversationDto start(@RequestBody ConversationStartRequest request) {
		var command = ConversationStartCommand.builder()
				.build();
		var conversation = chatService.startConversation(command);
		return conversationConvertor.toDto(conversation);
	}

}
