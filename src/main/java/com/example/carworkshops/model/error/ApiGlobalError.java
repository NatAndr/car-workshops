package com.example.carworkshops.model.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiGlobalError {
    private String code;
    @JsonProperty("object_name")
    private String objectName;
    private String message;
    @JsonIgnore
    private String error;

    public ApiGlobalError(String message) {
        this.message = message;
    }
}
