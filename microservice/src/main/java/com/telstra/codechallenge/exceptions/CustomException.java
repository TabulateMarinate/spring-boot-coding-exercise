package com.telstra.codechallenge.exceptions;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private int errorCode;

    public CustomException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
