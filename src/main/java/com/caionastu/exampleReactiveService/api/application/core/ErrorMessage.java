package com.caionastu.exampleReactiveService.api.application.core;


import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.ToString;

import java.util.Collections;
import java.util.Set;

@Builder
@ToString
public class ErrorMessage extends Throwable {

    @Builder.Default
    private Set<String> messages = Sets.newHashSet();

    public Set<String> getMessages() {
        return Collections.unmodifiableSet(messages);
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void addMessage(Set<String> messages) {
        this.messages.addAll(messages);
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }
}
