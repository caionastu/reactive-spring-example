package com.caionastu.exampleReactiveService.api.domain.example.vo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document(collection = "example")
public class Example {

    private String id;
    private String internalId;
    private String name;
    private String lastName;
    private String code;
    private int quantity;

    public void generateInternalId() {
        UUID uuid = UUID.randomUUID();
        setInternalId(uuid.toString());
    }
}
