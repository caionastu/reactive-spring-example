package com.caionastu.exampleReactiveService.api.application.core;


import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Set;

@Builder
@Setter
@ToString
public class ErrorBlock {

    @Getter
    private HttpStatus code;

    @Getter
    private String header;

    @Builder.Default
    private Set<ErrorMessage> errorMessages = Sets.newHashSet();

    public Set<ErrorMessage> getErrorMessages() {
        return Collections.unmodifiableSet(errorMessages);
    }

    public void addErrorMessage(String message) {
        errorMessages.add(ErrorMessage.builder().message(message).build());
    }

    public void addErrorMessages(Set<ErrorMessage> errorMessages) {
        this.errorMessages.addAll(errorMessages);
    }

    public boolean hasErrors() {
        return !errorMessages.isEmpty();
    }
}
