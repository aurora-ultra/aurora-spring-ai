package com.rakuten.ross.aurora.application;

import org.springframework.ai.chat.client.advisor.api.Advisor;

public interface ChatAdvisorSupplier {
	boolean support(ChatContext context);

	Advisor getAdvisor(ChatContext context);
}
