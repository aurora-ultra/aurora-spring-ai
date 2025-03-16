package com.rakuten.ross.auroraj.infra;

import com.rakuten.ross.auroraj.ability.knowledge.KnowledgeException;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Component
public class DefaultKnowledgeTransformer implements KnowledgeTransformer {

    @Override
    public Document toDocument(Path file) {
        try {
            var filename = file.getFileName().toString();
            return Document.builder()
                .id("aurora-knowledge-" + Base64.getEncoder().encodeToString(filename.getBytes()))
                .text(Files.readString(file))
                .metadata("title", filename)
                .build();
        } catch (IOException e) {
            throw new KnowledgeException("fail to read document : " + file, e);
        }

    }
}
