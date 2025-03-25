package com.rakuten.ross.aurora.ability.support;

import com.rakuten.ross.aurora.application.ablity.knowledge.support.ExternalUrlReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

class ExternalUrlReaderTest {
    ExternalUrlReader externalUrlReader = new ExternalUrlReader();

    @Test
    void testSupport() throws MalformedURLException {
        Assertions.assertFalse(externalUrlReader.support(new URL("http://example.com")));
        Assertions.assertFalse(externalUrlReader.support(new URL("http://www.example.com")));
        Assertions.assertFalse(externalUrlReader.support(new URL("http://a.example.com")));

        Assertions.assertFalse(externalUrlReader.support(new URL("https://example.com")));
        Assertions.assertFalse(externalUrlReader.support(new URL("https://www.example.com")));
        Assertions.assertFalse(externalUrlReader.support(new URL("https://a.example.com")));

        Assertions.assertTrue(externalUrlReader.support(new URL("http://example.com.cn")));
        Assertions.assertTrue(externalUrlReader.support(new URL("http://www.example.com.cn")));
        Assertions.assertTrue(externalUrlReader.support(new URL("http://a.example.com.cn")));

        Assertions.assertTrue(externalUrlReader.support(new URL("https://example.com.cn")));
        Assertions.assertTrue(externalUrlReader.support(new URL("https://www.example.com.cn")));
        Assertions.assertTrue(externalUrlReader.support(new URL("https://a.example.com.cn")));
    }

    @Test
    void testRead() throws MalformedURLException {
        var url = new URL("https://a.example.com.cn");
        var content = externalUrlReader.read(url);
        Assertions.assertNotNull(content);
    }
}

