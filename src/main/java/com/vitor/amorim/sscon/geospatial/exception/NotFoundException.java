package com.vitor.amorim.sscon.geospatial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String recurso, Long id) {
        super(String.format("%s  com id: %d não existe", recurso, id));
    }
}
