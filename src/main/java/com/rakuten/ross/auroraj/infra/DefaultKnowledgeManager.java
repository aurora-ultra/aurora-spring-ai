package com.rakuten.ross.auroraj.infra;

import com.rakuten.ross.auroraj.ability.knowledge.KnowledgeException;
import com.rakuten.ross.auroraj.ability.knowledge.KnowledgeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultKnowledgeManager implements KnowledgeManager {

    private final VectorStore vectorStore;
    private final KnowledgeTransformer knowledgeTransformer;

    @Override
    public void learn(Path root,Predicate<Path> filter) {
        try (var stream = Files.list(root)) {
            var documents = stream
                .map(knowledgeTransformer::toDocument)
                .collect(Collectors.toList());
            vectorStore.add(documents);
        } catch (IOException e) {
            throw new KnowledgeException("fail to list children in document root dir : " + root, e);
        }
    }
}
