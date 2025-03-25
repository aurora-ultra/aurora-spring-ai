package com.rakuten.ross.aurora.application.support;

import java.util.Optional;
import com.rakuten.ross.aurora.application.ChatAdvisorSupplier;
import com.rakuten.ross.aurora.application.ChatContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RagAdvisorSupplier implements ChatAdvisorSupplier {

	private final VectorStore vectorStore;

	@Override
	public boolean support(ChatContext context) {
		return context.getChatOption().isSearch();
	}

	@Override
	public Advisor getAdvisor(ChatContext context) {
		return new QuestionAnswerAdvisor(
				vectorStore,
				SearchRequest.builder()
						.topK(2)
						.build(),
				"""
						上下文信息如下，用 --------------------- 包围
						
						---------------------
						{question_answer_context}
						---------------------
						
						根据上下文和提供的历史信息（而非先验知识）回复用户评论。如果答案不在上下文中，请告知用户你无法回答该问题。
						"""
		);
	}

}
