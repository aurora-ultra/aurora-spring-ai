package com.rakuten.ross.aurora.application.ablity.knowledge;

import java.nio.file.Path;
import java.util.function.Predicate;

public interface KnowledgeManager {

    void learn(Path root, Predicate<Path> filter);

}
