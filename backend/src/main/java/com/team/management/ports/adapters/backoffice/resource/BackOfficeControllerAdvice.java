package com.team.management.ports.adapters.backoffice.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.ResponseEntity.badRequest;

@ControllerAdvice
@CrossOrigin(origins = "*")
public class BackOfficeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegal(Exception ex) {
        return badRequest().body(ex.getMessage());
    }
}
