package com.caionastu.exampleReactiveService.api.application.example_domain.controller;

import com.caionastu.exampleReactiveService.api.application.core.exceptions.BusinessException;
import com.caionastu.exampleReactiveService.api.application.example_domain.appService.ExampleAppService;
import com.caionastu.exampleReactiveService.api.application.example_domain.dto.ExampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "examples")
@Slf4j
public class ExampleController {

    private final ExampleAppService appService;

    public ExampleController(ExampleAppService exampleAppService) {
        this.appService = exampleAppService;
    }

    @GetMapping
    public Flux<ExampleDTO> findAll() {
        log.info("FindAll Request");
        return appService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<ExampleDTO>> findById(@PathVariable String id) {
        log.info("FindById Request");
        return appService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ExampleDTO>> create(@RequestBody ExampleDTO exampleDTO) {
        log.info("Creat Request");
        return appService.create(exampleDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(null)));
    }

    @PutMapping(path = "/{id}")
    public Mono<ResponseEntity<ExampleDTO>> update(@PathVariable String id, @RequestBody ExampleDTO exampleDTO) {
        log.info("Update Request");
        return appService.update(id, exampleDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(null)));
    }

    @DeleteMapping(path = "/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("Delete Request");

        return appService.findById(id)
                .flatMap(example -> appService.delete(id)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<ExampleDTO> streamAllExamples() {
        log.info("FindAll Stream Request");
        return appService.findAll();
    }

}
