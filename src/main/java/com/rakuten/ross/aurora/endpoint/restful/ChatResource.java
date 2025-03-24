package com.rakuten.ross.aurora.endpoint.restful;

import com.rakuten.ross.aurora.application.ChatConvertor;
import com.rakuten.ross.aurora.application.ChatService;
import com.rakuten.ross.aurora.application.command.ChatCommand;
import com.rakuten.ross.aurora.application.dto.AroMessageDto;
import com.rakuten.ross.aurora.endpoint.request.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatResource {

    private final ChatService chatService;
    private final ChatConvertor chatConvertor;

    @PostMapping("/chat/{conversionId}")
    public AroMessageDto completion(@PathVariable("conversionId") String conversionId,
                                    @RequestBody ChatRequest request) {
        var command = ChatCommand.builder()
            .conversationId(conversionId)
            .contents(chatConvertor.toDomain(request.getMessages()))
            .build();

        var reply = chatService.chat(command);

        return chatConvertor.toDto(reply);
    }
}
