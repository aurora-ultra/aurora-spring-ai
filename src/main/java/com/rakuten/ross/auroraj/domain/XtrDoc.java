package com.rakuten.ross.auroraj.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class XtrDoc {
    private String title;
    private String content;
}
