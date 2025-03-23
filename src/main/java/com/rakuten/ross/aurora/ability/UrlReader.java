package com.rakuten.ross.aurora.ability;

import java.net.URL;
import java.util.Optional;

public interface UrlReader {

    boolean support(URL url);

    Optional<String> read(URL url);

}
