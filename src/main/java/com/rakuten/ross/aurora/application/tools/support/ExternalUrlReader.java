package com.rakuten.ross.aurora.application.tools.support;

import java.net.URL;
import java.util.Optional;
import com.rakuten.ross.aurora.application.tools.UrlReader;
import org.springframework.stereotype.Component;

@Component
public class ExternalUrlReader implements UrlReader {
	public static final String domain = "example.com";

	@Override
	public boolean support(URL url) {
		return !url.getHost().endsWith(domain);
	}

	@Override
	public Optional<String> read(URL url) {
		return "".describeConstable();
	}
}
