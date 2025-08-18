package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

@ExceptionMapping(status = Response.Status.UNAUTHORIZED)
public class InvalidCredentialsException extends BfisException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
