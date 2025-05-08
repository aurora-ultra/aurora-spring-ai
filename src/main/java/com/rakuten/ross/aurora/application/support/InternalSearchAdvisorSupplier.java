package com.rakuten.ross.aurora.application.support;

import com.rakuten.ross.aurora.application.ChatAdvisorSupplier;
import com.rakuten.ross.aurora.application.ChatContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalSearchAdvisorSupplier implements ChatAdvisorSupplier {
	private final static int DEFAULT_TOP_K = 3;

	private final VectorStore vectorStore;

	private final static PromptTemplate USER_TEXT_ADVISE = PromptTemplate.builder()
			.template("""
					上下文信息如下，用 --------------------- 包围
					
					---------------------
					{question_answer_context}
					---------------------
					
					根据上下文和提供的历史信息（而非先验知识）回复用户问题。如果答案不在上下文中，请告知用户你无法回答该问题。
					""")
			.build();

	@Override
	public boolean support(ChatContext context) {
		return context.getChatOption().isEnableInternalSearch()
				&& !context.getChatOption().isEnableExternalSearch();
	}

	@Override
	public Advisor getAdvisor(ChatContext context) {
		return QuestionAnswerAdvisor.builder(vectorStore)
				.searchRequest(
						SearchRequest.builder()
								.topK(NumberUtils.max(context.getChatOption().getRetrieveTopK(), DEFAULT_TOP_K))
								.build()
				)
				.promptTemplate(USER_TEXT_ADVISE)
				.build();
	}

}
