package com.caionastu.exampleReactiveService.api.infrastructure.repository;

import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IExampleMongoRepository extends ReactiveMongoRepository<Example, String> {

}
