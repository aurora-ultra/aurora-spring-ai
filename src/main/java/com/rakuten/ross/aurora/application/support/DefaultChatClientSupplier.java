package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatClientSupplier;
import com.rakuten.ross.aurora.application.ChatContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultChatClientSupplier implements ChatClientSupplier {
	private final OpenAiChatModel chatModel;

	@Override
	public boolean support(ChatContext context) {
		return true;
	}

	@Override
	public ChatClient getChatClient(ChatContext context) {
		var options = ((OpenAiChatOptions) chatModel.getDefaultOptions())
				.copy();
		if (StringUtils.isNotBlank(context.getChatOption().getModel())) {
			options.setModel(context.getChatOption().getModel());
		}

		return ChatClient.builder(chatModel)
				.defaultOptions(options)
				.build();
	}
}
