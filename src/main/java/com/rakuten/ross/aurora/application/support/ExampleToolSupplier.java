package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatContext;
import com.rakuten.ross.aurora.application.ChatToolSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExampleToolSupplier implements ChatToolSupplier<ExampleTool> {

	private final ExampleTool exampleTool;

	@Override
	public String getName() {
		return "天气信息搜索";
	}

	@Override
	public String getDescription() {
		return """
				获取天气预报
				""";
	}

	@Override
	public boolean support(ChatContext context) {
		return context.getChatOption().isEnableExampleTools();
	}

	@Override
	public ExampleTool getTool(ChatContext context) {
		return exampleTool;
	}
}
