package com.vitor.amorim.sscon.geospatial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(String recurso, Long id) {
        super(String.format("%s já existe com id: %d", recurso, id));
    }
}
