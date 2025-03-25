package com.rakuten.ross.aurora.core.support;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeProvider {

    public LocalDateTime now(){
        return LocalDateTime.now();
    }
}
