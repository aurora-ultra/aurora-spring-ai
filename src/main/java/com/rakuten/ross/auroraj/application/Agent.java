package com.rakuten.ross.auroraj.application;

import com.rakuten.ross.auroraj.ability.tools.DateTimeTools;
import com.rakuten.ross.auroraj.ability.tools.DocumentTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Agent {

    private final ChatModel chatModel;
    private final DocumentTools documentTools;
    private final DateTimeTools dateTimeTools;
    private final RedisVectorStore vectorStore;

    private static final String DEFAULT_USER_TEXT_ADVISE = """
        
        上下文信息如下，用 --------------------- 包围
        
        ---------------------
        {question_answer_context}
        ---------------------
        
        根据上下文和提供的历史信息（而非先验知识）回复用户评论。如果答案不在上下文中，请告知用户你无法回答该问题。
        """;


    public ContentSummary summary(String userInput) {
        var advisor = new QuestionAnswerAdvisor(
            vectorStore,
            SearchRequest.builder()
                .topK(1)
                .build(),
            DEFAULT_USER_TEXT_ADVISE
        );
        return ChatClient.builder(chatModel)
            .build()
            .prompt()
//            .advisors(new SimpleLoggerAdvisor())
            .advisors(advisor)
            .system("你是一名经验丰富的互联网架构师,你会帮助用户查询资料，并且阅读整理，你会优先考虑通过上下文查找可用的文档链接，然后调用相关函数读取链接内容。")
//            .system("你是一名经验丰富的互联网架构师。")
            .user(userInput)
            .tools(documentTools)
            .call()
            .entity(ContentSummary.class);
    }


    public String testFunctionCall(String userInput) {
        return ChatClient.builder(chatModel)
            .build()
            .prompt()
            .tools(dateTimeTools)
            .system("你是聪明的助手，在合适的时候会调用工具")
            .user(userInput)
            .call()
            .content();
    }


}
