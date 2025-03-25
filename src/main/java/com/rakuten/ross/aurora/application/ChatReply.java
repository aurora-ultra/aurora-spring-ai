package com.rakuten.ross.aurora.application;

import com.rakuten.ross.aurora.domain.ChatMessage;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;

@Getter
@Builder
public class ChatReply {
	private final Flux<ChatMessage> reply;
	private final Flux<ChatMessageContent> contents;


}
