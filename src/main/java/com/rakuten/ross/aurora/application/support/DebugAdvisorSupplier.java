package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatAdvisorSupplier;
import com.rakuten.ross.aurora.application.ChatContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebugAdvisorSupplier implements ChatAdvisorSupplier {

	@Override
	public boolean support(ChatContext context) {
		return context.getChatOption().isEnableDebug();
	}

	@Override
	public Advisor getAdvisor(ChatContext context) {
		return new SimpleLoggerAdvisor();
	}

}
