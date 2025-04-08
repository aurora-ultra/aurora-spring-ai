package com.rakuten.ross.aurora.application;

import java.util.HashMap;
import java.util.Map;
import com.rakuten.ross.aurora.domain.Conversation;
import com.rakuten.ross.aurora.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.ai.chat.messages.UserMessage;


@Getter
@Builder
public class ChatContext {
	// when		who		what			where			how
	// -------------------------------------------------------------
	// now		user 	userMessage		conversation	chatOption

	private final Map<String, Object> attributes = new HashMap<>();

	private final User user;
	private final UserMessage userMessage;
	private final ChatOption chatOption;
	private final Conversation conversation;

	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, Class<T> ignored) {
		return (T) attributes.get(key);
	}

}
