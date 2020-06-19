package com.caionastu.exampleReactiveService.api.application.example_domain.appService;


import com.caionastu.exampleReactiveService.api.application.core.exceptions.BusinessException;
import com.caionastu.exampleReactiveService.api.application.example_domain.dto.ExampleAssemblerDTO;
import com.caionastu.exampleReactiveService.api.application.example_domain.dto.ExampleDTO;
import com.caionastu.exampleReactiveService.api.domain.example.service.ExampleService;
import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class ExampleAppService {

    private final ExampleService service;
    private final ExampleAssemblerDTO assembler;

    public ExampleAppService(ExampleService service) {
        this.service = service;
        this.assembler = new ExampleAssemblerDTO();
    }

    public Flux<ExampleDTO> findAll() {
        return service.findAll()
                .map(assembler::toDTO);
    }

    public Mono<ExampleDTO> findById(String id) {
        if (Strings.isNullOrEmpty(id)) {
            return Mono.empty();
        }

        return service.findById(id)
                .map(assembler::toDTO);
    }

    public Mono<ExampleDTO> create(ExampleDTO exampleDTO) {

        if (Objects.isNull(exampleDTO)) {
            return Mono.empty();
        }

        Example example = assembler.toEntity(exampleDTO);
        example.generateInternalId();

        return service.create(example)
                .map(assembler::toDTO)
                .doOnError(error -> log.error(error.getMessage()));
    }

    public Mono<ExampleDTO> update(String id, ExampleDTO exampleDTO) {
        Example entity = assembler.toEntity(exampleDTO);

        return service.update(id, entity)
                .map(assembler::toDTO)
                .doOnError(error -> log.error(error.getMessage()));
    }

    public Mono<Void> delete(String id) {
        return service.delete(id);
    }
}
