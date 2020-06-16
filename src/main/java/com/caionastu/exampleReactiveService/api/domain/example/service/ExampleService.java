package com.caionastu.exampleReactiveService.api.domain.example.service;

import com.caionastu.exampleReactiveService.api.application.core.ErrorKeys;
import com.caionastu.exampleReactiveService.api.application.core.ErrorMessage;
import com.caionastu.exampleReactiveService.api.application.core.exceptions.BusinessException;
import com.caionastu.exampleReactiveService.api.domain.example.validator.ExampleHasSameCodeValidator;
import com.caionastu.exampleReactiveService.api.domain.example.validator.IExamplePersistValidator;
import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;
import com.caionastu.exampleReactiveService.api.infrastructure.repository.ExampleRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ExampleService {

    private final ExampleRepository repository;
    private final ExampleHasSameCodeValidator validator;

    public ExampleService(ExampleRepository repository, ExampleHasSameCodeValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Flux<Example> findAll() {
        return repository.findAll();
    }

    public Mono<Example> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Example> create(Example example) {
        return persistValidators(example)
                .flatMap(errorMessage -> {
                    if (!errorMessage.isEmpty()) {
                        log.error(ErrorKeys.FAIL_TO_CREATE_ENTITY);
                        log.error("Object: " + example);
                        return Mono.error(new BusinessException(ErrorKeys.FAIL_TO_CREATE_ENTITY));
                    }

                    return repository.create(example);
                });
    }

    public Mono<Example> update(String id, Example example) {
        example.setId(id);


//        if (!errors.isEmpty()) {
//            log.error(ErrorKeys.FAIL_TO_UPDATE_ENTITY);
//            log.error("Object: " + example);
//            return Mono.error(new BusinessException(ErrorKeys.FAIL_TO_UPDATE_ENTITY, errors));
//        }

        return findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(ErrorKeys.ENTITY_NOT_FOUND)))
                .map(oldEntity -> {
                    oldEntity.setName(example.getName());
                    oldEntity.setLastName(example.getLastName());
                    oldEntity.setCode(example.getCode());
                    return oldEntity;
                })
                .flatMap(repository::update);
    }

    public Mono<Void> delete(String id) {
        return findById(id)
                .flatMap(example -> repository.delete(id));
    }

    private Mono<ErrorMessage> persistValidators(Example example) {
        return validator.validate(example);
    }
}
