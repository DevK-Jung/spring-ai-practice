package com.kjung.springaipractice.app.language.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageDetectionReqDto {
    @NotBlank
    @Schema(description = "대상", example = "こんにちは")
    private String text;
}
