package com.caionastu.exampleReactiveService.api.infrastructure.repository;

import com.caionastu.exampleReactiveService.api.domain.example.repository.IExampleRepository;
import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ExampleRepository implements IExampleRepository {

    private final IExampleMongoRepository mongoRepository;

    public ExampleRepository(IExampleMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    public Flux<Example> findAll() {
        return mongoRepository.findAll();
    }

    public Mono<Example> findById(String id) {
        return mongoRepository.findById(id);
    }

    public Mono<Example> create(Example example) {
        return mongoRepository.save(example);
    }

    public Mono<Example> update(Example example) {
        return mongoRepository.save(example);
    }

    public Mono<Void> delete(String id) {
        return mongoRepository.deleteById(id);
    }

    public Mono<Boolean> existByCode(String code) {
        return mongoRepository.existsByCode(code);
    }

}
