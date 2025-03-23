package com.rakuten.ross.aurora.endpoint;

import com.rakuten.ross.aurora.application.Agent;
import com.rakuten.ross.aurora.application.Trainer;
import com.rakuten.ross.aurora.application.model.ContentSummary;
import com.rakuten.ross.aurora.endpoint.request.LearnRequest;
import com.rakuten.ross.aurora.endpoint.request.SummaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgentController {

    private final Agent agent;
    private final Trainer trainer;

    @PostMapping("/learn")
    public void learn(@RequestBody LearnRequest learnRequest) {
        var type = learnRequest.getType();
        trainer.learn(type);
    }

    @PostMapping("/summary")
    public ContentSummary summary(@RequestBody SummaryRequest summaryRequest) {
        var userInput = summaryRequest.getInput();
        return agent.summary(userInput);
    }

    @PostMapping("/test")
    public String chat(@RequestBody SummaryRequest summaryRequest) {
        var userInput = summaryRequest.getInput();
        return agent.testFunctionCall(userInput);
    }
}