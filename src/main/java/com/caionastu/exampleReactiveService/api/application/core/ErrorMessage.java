package com.caionastu.exampleReactiveService.api.application.core;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private String domain;
    private String message;
}
