package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

@ExceptionMapping(status = Response.Status.UNAUTHORIZED)
public class AccountDisabledException extends BfisException {

    public AccountDisabledException(String message) {
        super(message);
    }
}
