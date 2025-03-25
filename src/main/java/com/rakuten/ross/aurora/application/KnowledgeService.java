package com.rakuten.ross.aurora.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KnowledgeService {

	private final VectorStore vectorStore;

	public void learn(String type) {
		var filter = (Predicate<Path>) path -> path.getFileName().endsWith("." + type);
		var root = Path.of("./material");
		learn(root, filter);
	}

	public void learn(Path root, Predicate<Path> filter) {
		var documents = loadDocuments(root, filter);
		vectorStore.add(documents);
	}

	private List<Document> loadDocuments(Path root, Predicate<Path> filter) {
		try (var stream = Files.list(root)) {
			return stream
					.filter(filter)
					.map(this::createDocument)
					.flatMap(Collection::stream)
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw KnowledgeException.of("fail to list children in document root dir : " + root, e);
		}
	}

	private List<Document> createDocument(Path path) {
		TextReader textReader = new TextReader(new FileSystemResource(path));
		textReader.getCustomMetadata().put(KnowledgeConstants.METADATA_FILE_NAME, path.getFileName().toString());
		return textReader.read();
	}


}
