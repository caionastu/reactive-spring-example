package com.caionastu.exampleReactiveService.api.domain.example.validator;

import com.caionastu.exampleReactiveService.api.application.core.ErrorKeys;
import com.caionastu.exampleReactiveService.api.application.core.ErrorMessage;
import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;
import com.caionastu.exampleReactiveService.api.infrastructure.repository.ExampleRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ExampleHasSameCodeValidator implements IExamplePersistValidator {

    private final ExampleRepository repository;

    public ExampleHasSameCodeValidator(ExampleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<ErrorMessage> validate(Example example) {

        return repository.existByCode(example.getCode())
                .flatMap(value -> {
                    ErrorMessage errorMessage = ErrorMessage.builder().build();
                    if (value) {
                        errorMessage.addMessage(ErrorKeys.CODE_ALREADY_EXISTS);
                    }
                    return Mono.just(errorMessage);
                });
    }
}
