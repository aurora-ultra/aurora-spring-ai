package com.rakuten.ross.aurora.core.layer;

import java.io.Serializable;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public interface Vo extends Serializable {
}
