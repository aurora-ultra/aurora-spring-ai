package com.rakuten.ross.aurora.ability.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

class InternalUrlReaderTest {

    InternalUrlReader aroUriReader = new InternalUrlReader();

    @BeforeEach
    void setUp() {

    }

    @Test
    void testSupport() throws MalformedURLException {
        Assertions.assertTrue(aroUriReader.support(new URL("http://example.com")));
        Assertions.assertTrue(aroUriReader.support(new URL("http://www.example.com")));
        Assertions.assertTrue(aroUriReader.support(new URL("http://a.example.com")));

        Assertions.assertTrue(aroUriReader.support(new URL("https://example.com")));
        Assertions.assertTrue(aroUriReader.support(new URL("https://www.example.com")));
        Assertions.assertTrue(aroUriReader.support(new URL("https://a.example.com")));

        Assertions.assertFalse(aroUriReader.support(new URL("http://example.com.cn")));
        Assertions.assertFalse(aroUriReader.support(new URL("http://www.example.com.cn")));
        Assertions.assertFalse(aroUriReader.support(new URL("http://a.example.com.cn")));

        Assertions.assertFalse(aroUriReader.support(new URL("https://example.com.cn")));
        Assertions.assertFalse(aroUriReader.support(new URL("https://www.example.com.cn")));
        Assertions.assertFalse(aroUriReader.support(new URL("https://a.example.com.cn")));
    }

    @Test
    void testRead() throws MalformedURLException {
        var url = new URL("https://example.com");
        var content = aroUriReader.read(url);
        Assertions.assertNotNull(content);
    }
}

