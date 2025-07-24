package com.kjung.springaipractice.app.translation.dto;

import com.kjung.springaipractice.app.translation.constants.LanguageCode;
import com.kjung.springaipractice.core.aiPipeline.marker.PipelineRequest;
import com.kjung.springaipractice.core.prompt.annotation.PromptVariable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslateReqDto extends TranslateCommonReqDto implements PipelineRequest {
    @PromptVariable
    @Schema(description = "text", example = "탄소중립 실현을 위한 온실가스 감축은 선택이 아닌 필수 과제가 되었다.")
    private String text;

    public TranslateReqDto(LanguageCode targetLang, String text) {
        super(targetLang);
        this.text = text;
    }
}
