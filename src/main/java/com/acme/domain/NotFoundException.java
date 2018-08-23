package com.acme.domain;

public class NotFoundException extends RuntimeException {

    private NotFoundException(final String message) {
        super(message);
    }

    public static NotFoundException entityNotFound(final Class<?> entityClass, final Object criteria) {
        return new NotFoundException(String.format("%s not found by %s", entityClass.getSimpleName(), criteria));
    }
}
