package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

@ExceptionMapping(status = Response.Status.UNAUTHORIZED)
public class RefreshTokenExpiredException extends BfisException {

    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
