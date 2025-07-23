package com.kjung.springaipractice.core.prompt.constants;

import lombok.Getter;

@Getter
public enum PromptType {
    DEFAULT("default");

    private final String name;

    PromptType(String name) {
        this.name = name;
    }
}
