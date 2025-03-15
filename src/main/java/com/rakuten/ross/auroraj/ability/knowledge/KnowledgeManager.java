package com.rakuten.ross.auroraj.ability.knowledge;

import java.nio.file.Path;
import java.util.function.Predicate;

public interface KnowledgeManager {
    void learn(Path root, Predicate<Path> filter);
}
