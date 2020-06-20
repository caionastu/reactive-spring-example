package com.caionastu.exampleReactiveService.api.domain.example.validator;

import com.caionastu.exampleReactiveService.api.application.core.ErrorBlock;
import com.caionastu.exampleReactiveService.api.application.error.ErrorKeys;
import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;
import com.caionastu.exampleReactiveService.api.infrastructure.repository.ExampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ExampleHasSameCodeValidator implements IExamplePersistValidator {

    private final ExampleRepository repository;

    public ExampleHasSameCodeValidator(ExampleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<ErrorBlock> validate(Example example) {

        return repository.existByCodeWithDifferentId(example.getCode(), example.getId())
                .flatMap(exist -> {
                    ErrorBlock errorBlock = ErrorBlock.builder().build();
                    if (exist) {
                        String errorKey = ErrorKeys.Example.CODE_ALREADY_EXISTS;
                        errorBlock.addErrorMessage(errorKey);
                        log.error(errorKey);
                    }
                    return Mono.just(errorBlock);
                });
    }
}
