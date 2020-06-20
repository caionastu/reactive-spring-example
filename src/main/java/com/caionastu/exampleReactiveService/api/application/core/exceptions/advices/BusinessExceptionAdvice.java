package com.caionastu.exampleReactiveService.api.application.core.exceptions.advices;

import com.caionastu.exampleReactiveService.api.application.core.ErrorBlock;
import com.caionastu.exampleReactiveService.api.application.core.exceptions.BusinessException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class BusinessExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ErrorBlock>> handleBusinessException(BusinessException exception) {
        HttpStatus code = exception.getErrorBlock().getCode();
        return Mono.just(ResponseEntity.status(code)
                .body(exception.getErrorBlock()));
    }
}
