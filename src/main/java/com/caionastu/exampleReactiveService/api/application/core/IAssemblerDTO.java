package com.caionastu.exampleReactiveService.api.application.core;

import java.util.List;
import java.util.stream.Collectors;

public interface IAssemblerDTO<DTO, Entity> {

    default List<DTO> toEntities(List<Entity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    default List<Entity> toDTOs(List<DTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    DTO toDTO(Entity entity);
    Entity toEntity(DTO dto);

}
