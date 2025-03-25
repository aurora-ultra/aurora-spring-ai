package com.rakuten.ross.aurora.application.command;

import java.util.List;
import com.rakuten.ross.aurora.domain.ChatMessageContent;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ChatCommand {

	private final String conversationId;
	private final ChatOption option;
	private final List<ChatMessageContent> contents;


}
