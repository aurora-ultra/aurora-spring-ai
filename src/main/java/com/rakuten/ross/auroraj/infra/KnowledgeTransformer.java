package com.rakuten.ross.auroraj.infra;


import org.springframework.ai.document.Document;

import java.nio.file.Path;

public interface KnowledgeTransformer {
    Document toDocument(Path path);
}
