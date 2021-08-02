package com.example.carworkshops.model.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiFieldError {
    @JsonProperty("object_name")
    private String objectName;
    private String field;
    private String code;
    @JsonProperty("rejected_value")
    private Object rejectedValue;
    private String message;
    @JsonIgnore
    private String error;
}
