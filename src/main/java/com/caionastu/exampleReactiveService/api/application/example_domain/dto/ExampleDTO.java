package com.caionastu.exampleReactiveService.api.application.example_domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExampleDTO {

    private String id;
    private String name;
    private String lastName;
    private String code;
    private int quantity;

}
