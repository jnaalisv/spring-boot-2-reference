package com.acme;

import com.acme.domain.NotFoundException;
import org.hibernate.StaleStateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { HibernateOptimisticLockingFailureException.class, StaleStateException.class})
    protected ResponseEntity<Object> handleHibernateOptimisticLockingFailureException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, "Conflict, please refresh", new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


}