package com.caionastu.exampleReactiveService.api.application.core.exceptions;

public class BusinessException extends Exception {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
    }
}
