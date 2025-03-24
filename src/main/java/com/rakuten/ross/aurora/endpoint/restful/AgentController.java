package com.rakuten.ross.aurora.endpoint.restful;

import com.rakuten.ross.aurora.application.AgentService;
import com.rakuten.ross.aurora.application.KnowledgeService;
import com.rakuten.ross.aurora.domain.AroSummary;
import com.rakuten.ross.aurora.endpoint.request.LearnRequest;
import com.rakuten.ross.aurora.endpoint.request.SummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final KnowledgeService knowledgeService;

    @PostMapping("/learn")
    public void learn(@RequestBody LearnRequest learnRequest) {
        var type = learnRequest.getType();
        knowledgeService.learn(type);
    }

    @PostMapping("/summary")
    public AroSummary summary(@RequestBody SummaryRequest summaryRequest) {
        var userInput = summaryRequest.getInput();
        return agentService.summary(userInput);
    }

    @PostMapping("/test")
    public String chat(@RequestBody SummaryRequest summaryRequest) {
        var userInput = summaryRequest.getInput();
        return agentService.testFunctionCall(userInput);
    }
}