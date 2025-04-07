package com.rakuten.ross.aurora.application;

import java.util.List;
import com.rakuten.ross.aurora.domain.ChatHistory;
import com.rakuten.ross.aurora.domain.ChatMessage;
import com.rakuten.ross.aurora.domain.Conversation;
import com.rakuten.ross.aurora.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@Builder
@With
public class ChatContext {

	private final User user;
	private final ChatMessage userMessage;
	private final ChatOption chatOption;
	private final Conversation conversation;
	private final ChatHistory chatHistory;

}
