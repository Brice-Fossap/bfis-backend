package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

@ExceptionMapping(status = Response.Status.UNAUTHORIZED)
public class InvalidRefreshTokenException extends BfisException {

    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
