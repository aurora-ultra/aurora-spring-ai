package com.rakuten.ross.aurora.application.ability.support;

import com.rakuten.ross.aurora.application.ability.KnowledgeConstants;
import com.rakuten.ross.aurora.application.ability.KnowledgeException;
import com.rakuten.ross.aurora.application.ability.KnowledgeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleKnowledgeManager implements KnowledgeManager {

    private final VectorStore vectorStore;

    @Override
    public void learn(Path root, Predicate<Path> filter) {
        var documents = loadDocuments(root);
        vectorStore.add(documents);
    }

    private List<Document> loadDocuments(Path root) {
        try (var stream = Files.list(root)) {
            return stream
                .map(this::toDocument)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw KnowledgeException.of("fail to list children in document root dir : " + root, e);
        }
    }

    private List<Document> toDocument(Path path) {
        TextReader textReader = new TextReader(new FileSystemResource(path));
        textReader.getCustomMetadata().put(KnowledgeConstants.METADATA_FILE_NAME, path.getFileName().toString());
        return textReader.read();
    }
}
