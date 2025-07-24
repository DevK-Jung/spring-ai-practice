package com.kjung.springaipractice.app.translation.dto;

import com.kjung.springaipractice.app.translation.constants.LanguageCode;
import com.kjung.springaipractice.core.prompt.annotation.PromptVariable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TranslateCommonReqDto {
    @PromptVariable
    @Schema(description = "변환 언어 코드", example = "EN")
    private LanguageCode targetLang;
}
