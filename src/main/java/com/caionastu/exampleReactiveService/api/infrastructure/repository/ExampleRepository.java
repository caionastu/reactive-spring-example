package com.caionastu.exampleReactiveService.api.infrastructure.repository;

import com.caionastu.exampleReactiveService.api.domain.example.repository.IExampleRepository;
import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;
import com.google.common.base.Strings;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ExampleRepository implements IExampleRepository {

    private final IExampleMongoRepository mongoRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    public ExampleRepository(IExampleMongoRepository mongoRepository, ReactiveMongoTemplate mongoTemplate) {
        this.mongoRepository = mongoRepository;
        this.mongoTemplate = mongoTemplate;
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

    public Mono<Boolean> existByCodeWithDifferentId(String code, String id) {
        ObjectId objectId = Strings.isNullOrEmpty(id) ? new ObjectId() : new ObjectId(id);

        Query query = new Query()
                .addCriteria(where("code").is(code))
                .addCriteria(where("_id").ne(objectId));

        return this.mongoTemplate.exists(query, Example.class);
    }

    public Mono<Boolean> existByNameWithDifferentId(String name, String id) {
        ObjectId objectId = Strings.isNullOrEmpty(id) ? new ObjectId() : new ObjectId(id);

        Query query = new Query()
                .addCriteria(where("name").is(name))
                .addCriteria(where("_id").ne(objectId));

        return this.mongoTemplate.exists(query, Example.class);
    }

}
