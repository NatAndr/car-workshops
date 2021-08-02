package com.example.carworkshops.util;

import com.example.carworkshops.model.DataResultObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpUtils {
    public static <T> ResponseEntity<DataResultObject<T>> ok(T data) {
        return new ResponseEntity<>(new DataResultObject<>(data), HttpStatus.OK);
    }

    public static ResponseEntity<DataResultObject<Void>> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static <T> ResponseEntity<DataResultObject<T>> withStatus(T data, HttpStatus status) {
        return new ResponseEntity<>(new DataResultObject<>(data), status);
    }
}

