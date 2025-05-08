package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatContext;
import com.rakuten.ross.aurora.application.ChatToolSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalSearchToolSupplier implements ChatToolSupplier<ExternalSearchTool> {

	private final ExternalSearchTool externalSearchTool;

	@Override
	public String getName() {
		return "互联网内容搜索";
	}

	@Override
	public String getDescription() {
		return "从互联网搜索新闻";
	}


	@Override
	public boolean support(ChatContext context) {
		return context.getChatOption().isEnableExternalSearch();
	}

	@Override
	public ExternalSearchTool getTool(ChatContext context) {
		return externalSearchTool;
	}
}
