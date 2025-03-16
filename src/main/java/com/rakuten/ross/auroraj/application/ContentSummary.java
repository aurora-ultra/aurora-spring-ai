package com.rakuten.ross.auroraj.application;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentSummary {

    private String url;

    private String title;

    private String summary;

    @JsonPropertyDescription("What role you are acting？")
    private String role;
}
