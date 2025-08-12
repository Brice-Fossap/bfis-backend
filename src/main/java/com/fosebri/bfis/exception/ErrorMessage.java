package com.fosebri.bfis.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ErrorMessage {

    private String message;
    private Instant date;
    private int status;

    public ErrorMessage(String message, int status) {
        this.message = message;
        this.date = Instant.now();
        this.status = status;
    }
}
