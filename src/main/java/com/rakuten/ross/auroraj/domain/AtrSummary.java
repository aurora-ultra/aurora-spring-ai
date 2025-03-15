package com.rakuten.ross.auroraj.domain;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AtrSummary {
    private String url;
    private String title;
    private String summary;
    @JsonPropertyDescription("你从什么角度分析的？")
    private String role;
}
