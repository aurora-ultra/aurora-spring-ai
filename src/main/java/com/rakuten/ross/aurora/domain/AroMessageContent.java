package com.rakuten.ross.aurora.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AroMessageContent {

    private String text;

    public static AroMessageContent of(String text) {
        return new AroMessageContent().setText(text);
    }
}
