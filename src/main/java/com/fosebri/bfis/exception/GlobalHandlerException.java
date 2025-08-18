package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalHandlerException implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception ex) {
        ExceptionMapping annotation = ex.getClass().getAnnotation(ExceptionMapping.class);

        if (annotation != null) {
            return Response
                    .status(annotation.status())
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ErrorMessage(
                            !annotation.message().isEmpty() ? annotation.message() : ex.getMessage(),
                            annotation.status().getStatusCode()
                    ))
                    .build();
        }

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorMessage(
                        "An unexpected error occurred",
                        Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
                ))
                .build();
    }
}