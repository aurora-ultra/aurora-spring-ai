package com.rakuten.ross.aurora.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conversation {
    private String id;

    public Conversation(String id) {
        this.id = id;
    }
}
