package com.kjung.springaipractice.app.translation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TranslateFileReqDto extends TranslateCommonReqDto {
    @NotNull
    @Schema(description = "파일")
    private MultipartFile file;
}
