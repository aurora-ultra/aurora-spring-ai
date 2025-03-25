package com.rakuten.ross.aurora.application.utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

@UtilityClass
public class ChatResponsesUtils {

	public String getTextContent(ChatResponse chatResponse) {
		return Optional.ofNullable(chatResponse)
				.map(ChatResponse::getResult)
				.map(Generation::getOutput)
				.map(AbstractMessage::getText)
				.orElse(null);
	}

	public String getTextContent(List<ChatResponse> chatResponses) {
		return chatResponses.stream()
				.map(ChatResponsesUtils::getTextContent)
				.filter(StringUtils::isNotBlank)
				.collect(Collectors.joining());
	}
}
