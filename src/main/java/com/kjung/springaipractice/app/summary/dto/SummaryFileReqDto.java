package com.kjung.springaipractice.app.summary.dto;

import com.kjung.springaipractice.core.prompt.annotation.PromptVariable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SummaryFileReqDto {
    @Min(5)
    @PromptVariable
    @Schema(description = "요약 길이", example = "10")
    private Integer summaryLength;
    @NotNull
    @Schema(description = "파일")
    private MultipartFile file;
}
