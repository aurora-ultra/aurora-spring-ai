package com.rakuten.ross.auroraj.endpoint.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LearnRequest {

    @Schema(example = "txt",defaultValue = "txt")
    private String type = "txt";

}
