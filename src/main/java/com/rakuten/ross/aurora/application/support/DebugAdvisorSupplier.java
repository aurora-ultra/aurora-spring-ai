package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatAdvisorSupplier;
import com.rakuten.ross.aurora.application.ChatContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebugAdvisorSupplier implements ChatAdvisorSupplier {

	@Override
	public boolean support(ChatContext context) {
		return false;
	}

	@Override
	public Advisor getAdvisor(ChatContext context) {
		return new SimpleLoggerAdvisor();
	}

}
