package com.rakuten.ross.aurora.application.tools;

import com.rakuten.ross.aurora.application.ChatTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebSearchTools implements ChatTool {

	@Tool(description = "互联网搜索新闻")
	public String search(String search) {
		log.info("search from internet: {}", search);
		return """
				搜索结果：你好，我是地球
				""";
	}

}