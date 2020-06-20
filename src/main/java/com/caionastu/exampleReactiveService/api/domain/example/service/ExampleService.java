package com.caionastu.exampleReactiveService.api.domain.example.service;

import com.caionastu.exampleReactiveService.api.application.error.ErrorKeys;
import com.caionastu.exampleReactiveService.api.application.core.exceptions.BusinessException;
import com.caionastu.exampleReactiveService.api.domain.example.validator.IExamplePersistValidator;
import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;
import com.caionastu.exampleReactiveService.api.infrastructure.repository.ExampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ExampleService {

    private final ExampleRepository repository;
    private final Flux<IExamplePersistValidator> validators;

    public ExampleService(ExampleRepository repository, ListableBeanFactory listableBeanFactory) {
        this.repository = repository;
        this.validators = Flux.fromIterable(listableBeanFactory.getBeansOfType(IExamplePersistValidator.class).values());
    }

    public Flux<Example> findAll() {
        return repository.findAll();
    }

    public Mono<Example> findById(String id) {
        return repository.findById(id);
    }

    public Mono<Example> create(Example example) {
        return validators.flatMap(validator -> validator.validate(example))
                .reduce((blockTotalize, currentBlock) -> {
                    blockTotalize.addErrorMessages(currentBlock.getErrorMessages());
                    return blockTotalize;
                })
                .flatMap(errorBlock -> {
                    if (errorBlock.hasErrors()) {
                        errorBlock.setCode(HttpStatus.PRECONDITION_FAILED);
                        errorBlock.setHeader(ErrorKeys.Common.FAIL_TO_CREATE_ENTITY);
                        return Mono.error(new BusinessException(errorBlock));
                    }
                    return repository.create(example);
                });
    }

    public Mono<Example> update(String id, Example example) {
        example.setId(id);

        return validators.flatMap(validator -> validator.validate(example))
                .reduce((blockTotalize, currentBlock) -> {
                    blockTotalize.addErrorMessages(currentBlock.getErrorMessages());
                    return blockTotalize;
                })
                .flatMap(errorBlock -> {
                    if (errorBlock.hasErrors()) {
                        errorBlock.setCode(HttpStatus.PRECONDITION_FAILED);
                        errorBlock.setHeader(ErrorKeys.Common.FAIL_TO_UPDATE_ENTITY);
                        return Mono.error(new BusinessException(errorBlock));
                    }

                    return findById(id);
                })
                .switchIfEmpty(Mono.error(new BusinessException(ErrorKeys.Common.ENTITY_NOT_FOUND)))
                .flatMap(oldEntity -> {
                    oldEntity.setName(example.getName());
                    oldEntity.setLastName(example.getLastName());
                    oldEntity.setCode(example.getCode());
                    return repository.update(oldEntity);
                });
    }

    public Mono<Void> delete(String id) {
        return findById(id)
                .flatMap(example -> repository.delete(id));
    }

}
