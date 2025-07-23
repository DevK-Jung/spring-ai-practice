package com.kjung.springaipractice.app.translation.constants;

import lombok.Getter;

@Getter
public enum LanguageCode {
    KO("Korean"),
    EN("English"),
    JA("Japanese"),
    ZH("Chinese"),
    ES("Spanish"),
    FR("French"),
    DE("German"),
    IT("Italian"),
    PT("Portuguese"),
    RU("Russian");

    private final String name;

    LanguageCode(String name) {
        this.name = name;
    }
}
