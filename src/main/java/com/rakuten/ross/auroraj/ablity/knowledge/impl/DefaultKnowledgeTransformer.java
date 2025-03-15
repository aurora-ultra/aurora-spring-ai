package com.rakuten.ross.auroraj.ablity.knowledge.impl;

import com.rakuten.ross.auroraj.ablity.knowledge.KnowledgeException;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DefaultKnowledgeTransformer implements KnowledgeTransformer {

    @Override
    public Document toDocument(Path file) {
        try {
            return Document.builder()
                .text(Files.readString(file))
                .metadata("title", file.getFileName().toString())
                .build();
        } catch (IOException e) {
            throw new KnowledgeException("fail to read document  : " + file, e);
        }

    }
}
