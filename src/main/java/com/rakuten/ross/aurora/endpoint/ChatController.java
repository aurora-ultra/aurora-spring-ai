package com.rakuten.ross.aurora.endpoint;

import com.rakuten.ross.aurora.application.ChatService;
import com.rakuten.ross.aurora.endpoint.request.ChatRequest;
import com.rakuten.ross.aurora.endpoint.request.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ChatResponse completion(@RequestBody ChatRequest chatRequest) {
        var message = chatRequest.getMessage();
        var reply = chatService.chat(message);
        return ChatResponse.builder()
            .id(UUID.randomUUID().toString())
            .reply(reply)
            .build();

    }
}
