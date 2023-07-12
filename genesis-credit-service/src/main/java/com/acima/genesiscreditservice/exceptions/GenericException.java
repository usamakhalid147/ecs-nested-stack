package com.acima.genesiscreditservice.exceptions;

import java.io.Serial;

public class GenericException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public GenericException(Integer statusCode, String message) {
        super(String.format("Failed for [%s]: %s", statusCode, message));
    }
}
