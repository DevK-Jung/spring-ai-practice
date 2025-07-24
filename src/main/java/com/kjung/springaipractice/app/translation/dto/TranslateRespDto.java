package com.kjung.springaipractice.app.translation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslateRespDto {
    private String result;
    private String languageCode;
}
