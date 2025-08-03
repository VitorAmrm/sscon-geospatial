package com.vitor.amorim.sscon.geospatial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AgeOutputInvalidException extends RuntimeException {

    public AgeOutputInvalidException() {
        super("Parâmetro 'output' utilizado é inválido");
    }
}
