package com.rakuten.ross.aurora.application;

import com.rakuten.ross.aurora.application.ablity.knowledge.KnowledgeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KnowledgeService {

    private final KnowledgeManager knowledgeManager;

    public void learn(String type) {
        var filter = (Predicate<Path>) path -> path.getFileName().endsWith("." + type);
        var root = Path.of("./material");
        knowledgeManager.learn(root, filter);
    }


}
