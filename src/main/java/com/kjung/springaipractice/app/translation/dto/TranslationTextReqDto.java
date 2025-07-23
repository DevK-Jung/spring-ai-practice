package com.kjung.springaipractice.app.translation.dto;

import com.kjung.springaipractice.core.prompt.annotation.PromptVariable;
import com.kjung.springaipractice.core.aiPipeline.marker.PipelineRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslationTextReqDto extends TranslationCommonReqDto implements PipelineRequest {
    @PromptVariable
    private String text;
}
