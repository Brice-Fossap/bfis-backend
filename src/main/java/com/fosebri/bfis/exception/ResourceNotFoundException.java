package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

@ExceptionMapping(status = Response.Status.NOT_FOUND)
public class ResourceNotFoundException extends BfisException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
