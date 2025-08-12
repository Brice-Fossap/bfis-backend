package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

@ExceptionMapping(status = Response.Status.CONFLICT)
public class ChangePasswordException extends BfisException {

    public ChangePasswordException(String message) {
        super(message);
    }
}
