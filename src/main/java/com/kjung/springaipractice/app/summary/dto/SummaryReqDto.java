package com.kjung.springaipractice.app.summary.dto;

import com.kjung.springaipractice.core.aiPipeline.marker.PipelineRequest;
import com.kjung.springaipractice.core.prompt.annotation.PromptVariable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class SummaryReqDto implements PipelineRequest {
    @Min(5)
    @PromptVariable
    @Schema(description = "요약 길이", example = "10")
    private Integer summaryLength;
    @NotBlank
    @PromptVariable
    @Schema(description = "대상", example = "탄소중립 실현을 위한 온실가스 감축은 선택이 아닌 필수 과제가 되었다.")
    private String text;

    public SummaryReqDto(Integer summaryLength, String text) {
        this.summaryLength = summaryLength;
        this.text = text;
    }
}
