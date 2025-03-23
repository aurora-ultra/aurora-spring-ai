package com.rakuten.ross.aurora.ability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentTools {

    private final List<UrlReader> urlReaders;

    private static URL parseUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw DocumentToolsException.of("the url is not valid", e);
        }
    }

    @Tool(description = "查看某个文档内容")
    public Optional<String> readUrl(@ToolParam(description = "url") String url) {
        log.info("read url = {}", url);

        var aUrl = parseUrl(url);

        return this.findUriReader(aUrl).flatMap(documentLoader -> documentLoader.read(aUrl));
    }

    private Optional<UrlReader> findUriReader(URL url) {
        return urlReaders.stream()
            .filter(documentLoader -> documentLoader.support(url))
            .findFirst();
    }


}
