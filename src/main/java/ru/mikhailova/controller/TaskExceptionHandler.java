package ru.mikhailova.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
public class TaskExceptionHandler {
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<String> notFound(Exception e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOTHING THERE");
    }
}
