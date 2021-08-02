package com.example.carworkshops.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DataResultObject<T> {

    @JsonProperty(value = "data")
    private final T data;

    public DataResultObject(T data) {
        this.data = data;
    }
}
