package com.example.carworkshops.controller.exception;

import com.example.carworkshops.model.error.ApiErrorsView;
import com.example.carworkshops.model.error.ApiFieldError;
import com.example.carworkshops.model.error.ApiGlobalError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request
    ) {
        BindingResult bindingResult = e.getBindingResult();

        List<ApiFieldError> apiFieldErrors = bindingResult.getFieldErrors().stream()
            .map(fieldError -> ApiFieldError.builder()
                .objectName(fieldError.getObjectName())
                .field(fieldError.getField())
                .rejectedValue(fieldError.getRejectedValue())
                .message(fieldError.getDefaultMessage())
                .build()
            )
            .collect(toList());

        List<ApiGlobalError> apiGlobalErrors = bindingResult.getGlobalErrors().stream()
            .map(globalError -> ApiGlobalError.builder()
                .code(globalError.getCode())
                .objectName(globalError.getObjectName())
                .message(globalError.getDefaultMessage())
                .build()
            )
            .collect(toList());

        ApiErrorsView apiErrorsView = new ApiErrorsView(apiFieldErrors, apiGlobalErrors, null);

        return new ResponseEntity<>(apiErrorsView, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleEntityNotFound(NoSuchElementException e) {
        logger.error("Caught error", e);

        ApiGlobalError apiGlobalError = ApiGlobalError.builder()
            .message(e.getMessage())
            .build();

        return buildApiGlobalErrorResponseEntity(apiGlobalError, HttpStatus.NOT_FOUND, "Not found");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception e) {
        logger.error("Uncaught error", e);

        ApiGlobalError apiGlobalError = ApiGlobalError.builder()
            .message(e.getMessage())
            .build();

        return buildApiGlobalErrorResponseEntity(apiGlobalError, HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
    }

    private ResponseEntity<Object> buildApiGlobalErrorResponseEntity(
        ApiGlobalError apiGlobalError,
        HttpStatus httpStatus,
        String error) {

        ApiErrorsView apiErrorsView = ApiErrorsView.builder()
            .fieldErrors(Collections.emptyList())
            .globalErrors(Collections.singletonList(apiGlobalError))
            .error(error)
            .build();

        return new ResponseEntity<>(apiErrorsView, httpStatus);
    }
}
