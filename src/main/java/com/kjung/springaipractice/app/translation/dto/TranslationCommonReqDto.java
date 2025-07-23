package com.kjung.springaipractice.app.translation.dto;

import com.kjung.springaipractice.app.translation.constants.LanguageCode;
import com.kjung.springaipractice.core.prompt.annotation.PromptVariable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TranslationCommonReqDto {
    @PromptVariable
    private LanguageCode targetLang;
}
