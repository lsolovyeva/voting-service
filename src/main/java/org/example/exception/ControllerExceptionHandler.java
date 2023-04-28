package org.example.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundError(EntityNotFoundException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage("Error: entity does not exist.");
        log.error("Error id={}", errorMessage.getId().toString(), e.getCause() == null ? e : e.getCause());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> dataIntegrityError(HttpServletRequest req, DataIntegrityViolationException e) {
        ErrorMessage errorMessage = new ErrorMessage("Error: data integrity is not preserved.");
        log.error("Error id={}", errorMessage.getId().toString(), e.getRootCause() == null ? e : e.getRootCause());
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorMessage> unsupportedOperationError(HttpServletRequest req, UnsupportedOperationException e) {
        ErrorMessage errorMessage = new ErrorMessage("Error: action cannot be performed.");
        log.error("Error id={}", errorMessage.getId().toString(), e.getCause() == null ? e : e.getCause());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /*private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(e)  == null ? e : NestedExceptionUtils.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType, rootCause.toString());
    }

    public static class ErrorInfo {
        private final String url;
        private final ErrorType type;
        private final String detail;

        public ErrorInfo(CharSequence url, ErrorType type, String detail) {
            this.url = url.toString();
            this.type = type;
            this.detail = detail;
        }
    }

    public enum ErrorType {
        APP_ERROR,
        DATA_NOT_FOUND,
        DATA_ERROR,
        VALIDATION_ERROR
    }*/
}
