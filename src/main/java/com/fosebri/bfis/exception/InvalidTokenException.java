package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

@ExceptionMapping(status = Response.Status.UNAUTHORIZED)
public class InvalidTokenException extends BfisException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
