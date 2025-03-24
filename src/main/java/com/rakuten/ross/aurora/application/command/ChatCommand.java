package com.rakuten.ross.aurora.application.command;

import com.rakuten.ross.aurora.domain.AroMessageContent;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ChatCommand {
    private final String conversationId;
    private final List<AroMessageContent> contents;
}
