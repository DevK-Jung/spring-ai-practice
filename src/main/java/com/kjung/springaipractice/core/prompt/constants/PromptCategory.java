package com.kjung.springaipractice.core.prompt.constants;

import lombok.Getter;

@Getter
public enum PromptCategory {
    TRANSLATION("translation"),
    LANGUAGE_DETECTION("language-detection");

    private final String name;

    PromptCategory(String name) {
        this.name = name;
    }
}
