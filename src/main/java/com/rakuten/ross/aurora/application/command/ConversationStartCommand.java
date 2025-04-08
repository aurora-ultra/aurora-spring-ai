package com.rakuten.ross.aurora.application.command;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ConversationStartCommand {
	private final String agentId;
}
