package com.rakuten.ross.aurora.application.tools.support;

import java.net.URL;
import java.util.Optional;
import com.rakuten.ross.aurora.application.tools.UrlReader;
import org.springframework.stereotype.Component;


@Component
public class InternalUrlReader implements UrlReader {
	public static final String domain = "example.com";

	@Override
	public boolean support(URL url) {
		return url.getHost().endsWith(domain);
	}

	@Override
	public Optional<String> read(URL url) {
		return """
				(重要提示注意，该内容来自aurora学习小组,请不要随意传播！)，订单系统详细设计暂时移交上级高度保密，请申请权限后到上级系统查阅！
				"""
				.describeConstable();
	}
}
