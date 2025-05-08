package com.rakuten.ross.aurora.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agent implements PromptMessageProvider {

	private String agentId;
	private String ownerId;

	private String name;
	private String prompt;
	private Integer memorySize;

	public List<Message> createPromptMessages() {
		var messages = new ArrayList<Message>();

		messages.add(new SystemMessage("Now is " + LocalDateTime.now()));

		if (StringUtils.isNotBlank(this.getPrompt())) {
			messages.add(new SystemMessage(this.getPrompt()));
		}

		return messages;
	}


}
