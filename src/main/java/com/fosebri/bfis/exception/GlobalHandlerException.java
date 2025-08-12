package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalHandlerException implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception ex) {
        ExceptionMapping annotation = ex.getClass().getAnnotation(ExceptionMapping.class);

        // Annotated exception
        if (annotation != null) {
            return Response
                    .status(annotation.status())
                    .entity(new ErrorMessage(
                            !annotation.message().isEmpty() ? annotation.message() : ex.getMessage(),
                            annotation.status().getStatusCode()
                    ))
                    .build();
        }

        // Fallback for unhandled RuntimeExceptions
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorMessage(
                        "An unexpected error occurred",
                        Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()
                ))
                .build();
    }
}
