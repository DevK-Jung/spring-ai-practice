package com.kjung.springaipractice.app.language.controller;

import com.kjung.springaipractice.app.language.dto.LanguageDetectionReqDto;
import com.kjung.springaipractice.app.language.dto.LanguageDetectionRespDto;
import com.kjung.springaipractice.app.language.service.LanguageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "언어")
@RestController
@RequiredArgsConstructor
@RequestMapping("/language")
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/detect")
    public LanguageDetectionRespDto languageDetect(LanguageDetectionReqDto param) {
        return languageService.languageDetect(param.getText());
    }

}
