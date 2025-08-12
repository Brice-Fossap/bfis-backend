package com.fosebri.bfis.exception;

import jakarta.ws.rs.core.Response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExceptionMapping {
    Response.Status status() default Response.Status.INTERNAL_SERVER_ERROR;

    String message() default "";
}
