package com.caionastu.exampleReactiveService.api.application.core;

import reactor.core.publisher.Mono;

public interface IEntityValidator<Entity> {

    Mono<ErrorMessage> validate(Entity entity);
}
