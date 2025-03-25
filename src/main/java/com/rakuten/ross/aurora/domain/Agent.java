package com.rakuten.ross.aurora.domain;

import java.util.ArrayList;
import java.util.List;
import com.rakuten.ross.aurora.core.layer.Model;
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
public class Agent implements Model, PromptMessageCreator {

	private String agentId;
	private String name;
	private String ownerId;
	private String prompt;

	public List<Message> createPromptMessages() {
		var messages = new ArrayList<Message>();

		if (StringUtils.isNotBlank(this.getPrompt())) {
			messages.add(new SystemMessage(this.getPrompt()));
		}

		return messages;
	}


}
