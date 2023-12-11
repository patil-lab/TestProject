package com.example.testproject.handler;


import com.example.testproject.dto.client.TestServiceResult;
import com.example.testproject.exception.ApplicationeError;
import com.example.testproject.exception.TestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            GlobalExceptionHandler.class);

    @Value("${spring.application.name}")
    private String serviceName;

    private Gson gson = new GsonBuilder().create();


    // ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleApplicationException(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TestException.class)
    public ResponseEntity<TestServiceResult<ApplicationeError>> handleApplicationException(TestException ex, HttpServletRequest request) {
        ApplicationeError error = ex.getError();
        LOGGER.error(ex.getMessage(),ex);

        return ResponseEntity.ok(TestServiceResult.fail(error));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<TestServiceResult<String>> handle(HttpClientErrorException ex) {
        LOGGER.error(ex.getMessage(),ex);
        return ResponseEntity.ok(TestServiceResult.fail(ex.getMessage()));
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handle(HttpServerErrorException ex) {
        LOGGER.error(ex.getMessage(),ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getObjectResponseEntity(BindingResult bindingResult, int code, HttpStatus status) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<JsonObject> fields = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            JsonObject field = new JsonObject();
            field.addProperty("field", fieldError.getField());
            field.addProperty("message", fieldError.getDefaultMessage());
            fields.add(field);
        }

        String message = this.gson.toJson(fields);
        return this.createResponseEntity(message, code, status);
    }

    private ResponseEntity<Object> createResponseEntity(String message, int code, HttpStatus status) {
        TestException clientErrorException = new TestException(this.serviceName, message, code);
        return new ResponseEntity<>(clientErrorException.toJson(), status);
    }


}
