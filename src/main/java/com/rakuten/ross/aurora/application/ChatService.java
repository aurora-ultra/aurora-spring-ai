package com.rakuten.ross.aurora.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final OpenAiChatModel chatModel;

    public String chat(String message) {
        return creatChatClient()
            .prompt()
            .user(message)
            .call()
            .content();
    }

    private ChatClient creatChatClient() {
        return ChatClient.builder(chatModel)
            .build();
    }

}
