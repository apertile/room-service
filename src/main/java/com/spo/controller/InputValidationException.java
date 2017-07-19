package com.spo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InputValidationException extends RuntimeException {

    private static final long serialVersionUID = -7848768910715012232L;

    public InputValidationException(String message) {
        super(message);
    }

}
