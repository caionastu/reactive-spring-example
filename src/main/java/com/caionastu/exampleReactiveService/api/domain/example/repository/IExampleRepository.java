package com.caionastu.exampleReactiveService.api.domain.example.repository;

import reactor.core.publisher.Mono;

public interface IExampleRepository {

    Mono<Boolean> existByNameWithDifferentId(String name, String id);

    Mono<Boolean> existByCodeWithDifferentId(String code, String id);
}