package com.github.lsolovyeva.voting.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundError(EntityNotFoundException e, WebRequest request) {
        return createErrorResponse("Error: entity does not exist.", e, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> dataIntegrityError(HttpServletRequest req, DataIntegrityViolationException e) {
        return createErrorResponse("Error: data integrity is not preserved.", e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorMessage> unsupportedOperationError(HttpServletRequest req, UnsupportedOperationException e) {
        return createErrorResponse("Error: action cannot be performed.", e, HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<ErrorMessage> createErrorResponse(String error, Exception e, HttpStatus status) {
        ErrorMessage errorMessage = new ErrorMessage(error);
        log.info("Error id={}, message={}", errorMessage.getId().toString(), e.getCause() == null ? e : e.getCause());
        return new ResponseEntity<>(errorMessage, status);
    }
}
