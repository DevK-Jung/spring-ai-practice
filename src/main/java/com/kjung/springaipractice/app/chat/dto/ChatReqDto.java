package com.kjung.springaipractice.app.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatReqDto {
    @NotBlank
    @Schema(description = "대상", example = "안녕? 내 이름은 K야")
    private String text;
    @Schema(description = "대화 ID", example = "conv_1753336667165_c030368c")
    private String conversationId;
}
