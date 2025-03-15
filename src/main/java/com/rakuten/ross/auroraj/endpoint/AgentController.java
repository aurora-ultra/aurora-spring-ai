package com.rakuten.ross.auroraj.endpoint;

import com.rakuten.ross.auroraj.application.Agent;
import com.rakuten.ross.auroraj.application.Trainer;
import com.rakuten.ross.auroraj.application.AgentSummary;
import com.rakuten.ross.auroraj.endpoint.request.ChatRequest;
import com.rakuten.ross.auroraj.endpoint.request.LearnRequest;
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

    @PostMapping("/chat")
    public AgentSummary chat(@RequestBody ChatRequest chatRequest) {
        var userInput = chatRequest.getInput();
        // todo need some dispatcher for the user input for different handlers.
        return agent.readXtrDocument(userInput);
    }
}