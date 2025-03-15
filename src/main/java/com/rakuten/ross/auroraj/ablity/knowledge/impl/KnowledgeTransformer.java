package com.rakuten.ross.auroraj.ablity.knowledge.impl;


import org.springframework.ai.document.Document;

import java.nio.file.Path;

public interface KnowledgeTransformer {
    Document toDocument(Path path);
}
