package com.rakuten.ross.aurora.application.command;

import com.rakuten.ross.aurora.application.ChatContext;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;

@Getter
@Builder
public class ChatReply {
	private final ChatContext context;
	private final Flux<ChatMessageContent> contents;
}
