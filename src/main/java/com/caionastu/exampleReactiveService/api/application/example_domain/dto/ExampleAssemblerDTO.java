package com.caionastu.exampleReactiveService.api.application.example_domain.dto;


import com.caionastu.exampleReactiveService.api.application.core.IAssemblerDTO;
import com.caionastu.exampleReactiveService.api.domain.example.vo.Example;

public class ExampleAssemblerDTO implements IAssemblerDTO<ExampleDTO, Example> {

    @Override
    public ExampleDTO toDTO(Example example) {
        return ExampleDTO.builder()
                .id(example.getId())
                .code(example.getCode())
                .name(example.getName())
                .lastName(example.getLastName())
                .quantity(example.getQuantity())
                .build();
    }

    @Override
    public Example toEntity(ExampleDTO exampleDTO) {
        return Example.builder()
                .id(exampleDTO.getId())
                .code(exampleDTO.getCode())
                .name(exampleDTO.getName())
                .lastName(exampleDTO.getLastName())
                .quantity(exampleDTO.getQuantity())
                .build();
    }
}
