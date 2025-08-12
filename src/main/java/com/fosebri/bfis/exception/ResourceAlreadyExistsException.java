package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

@ExceptionMapping(status = Response.Status.CONFLICT)
public class ResourceAlreadyExistsException extends BfisException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
