package com.rakuten.ross.aurora.domain;

import java.util.List;
import org.springframework.ai.chat.messages.Message;

public interface PromptMessageCreator {

	List<Message> createPromptMessages();
}
