package com.fosebri.bfis.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalHandlerException implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception ex) {

        if (ex instanceof ConstraintViolationException cve) {
            String errorMessage = cve.getConstraintViolations().stream()
                    .findFirst()
                    .map(ConstraintViolation::getMessage)
                    .orElse("Validation error");

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(
                            errorMessage,
                            Response.Status.BAD_REQUEST.getStatusCode()
                    ))
                    .build();
        }

        if (ex instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(new ErrorMessage(
                            ex.getMessage(),
                            Response.Status.BAD_REQUEST.getStatusCode()
                    ))
                    .build();
        }

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