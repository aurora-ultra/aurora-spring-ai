package com.rakuten.ross.auroraj.infra;

import com.rakuten.ross.auroraj.ability.tools.DocumentTools;
import com.rakuten.ross.auroraj.application.Agent;
import com.rakuten.ross.auroraj.application.AgentSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultAgent implements Agent {

    private final ChatModel chatModel;
    private final DocumentTools documentTools;
    private final RedisVectorStore vectorStore;

    @Override
    public AgentSummary readXtrDocument(String userInput) {
        return ChatClient.builder(chatModel)
            .build()
            .prompt()
            .advisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().topK(1).build()))
//            .system("You will search document url under the context, and call the system functions to get the content, then summary the content to the user.")
            .system("你是一名经验丰富的互联网架构师。")
            .user(userInput)
            .tools(documentTools)
            .advisors(new SimpleLoggerAdvisor())
            .call()
            .entity(AgentSummary.class);
    }


}
