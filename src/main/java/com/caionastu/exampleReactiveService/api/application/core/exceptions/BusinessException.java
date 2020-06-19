package com.caionastu.exampleReactiveService.api.application.core.exceptions;


import com.caionastu.exampleReactiveService.api.application.core.ErrorBlock;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessException extends RuntimeException {

    @Getter
    private ErrorBlock errorBlock;

    public BusinessException(ErrorBlock errorBlock) {
        super(errorBlock.getHeader());
        this.errorBlock = errorBlock;
    }

    public BusinessException(String message) {
        super(message);
        this.errorBlock = ErrorBlock.builder()
                .header(message)
                .build();
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
    }

    public void logErrors() {
        this.errorBlock.getErrorMessages()
                .forEach(errorMessage -> log.error(errorMessage.getMessage()));
    }

}
