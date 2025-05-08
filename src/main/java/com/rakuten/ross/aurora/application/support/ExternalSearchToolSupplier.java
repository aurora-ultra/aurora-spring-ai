package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatContext;
import com.rakuten.ross.aurora.application.ChatTool;
import com.rakuten.ross.aurora.application.ChatToolSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class ExternalSearchToolSupplier implements ChatToolSupplier {

	private final ExternalSearchTool externalSearchTool;

	@Override
	public boolean support(ChatContext context) {
		return context.getChatOption().isEnableExternalSearch();
	}

	@Override
	public ChatTool getTool(ChatContext context) {
		return externalSearchTool;
	}
}
