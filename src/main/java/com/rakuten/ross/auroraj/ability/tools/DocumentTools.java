package com.rakuten.ross.auroraj.ability.tools;

import com.rakuten.ross.auroraj.domain.XtrDocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentTools {

    private final XtrDocService xtrDocService;

    @Tool(description = "查看某个文档内容")
    public String readUrl(@ToolParam(description = "url") String url) {
        log.info("readXtrDoc url={}", url);
        if (url.contains("example.com")) {
            var doc = xtrDocService.getByUrl(url);
            return doc.getContent();
        } else {
            return WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        }
    }


}
