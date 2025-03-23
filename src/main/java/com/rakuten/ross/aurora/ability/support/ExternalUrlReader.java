package com.rakuten.ross.aurora.ability.support;

import com.rakuten.ross.aurora.ability.UrlReader;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;

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
