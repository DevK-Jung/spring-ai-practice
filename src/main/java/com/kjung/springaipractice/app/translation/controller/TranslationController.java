package com.kjung.springaipractice.app.translation.controller;

import com.kjung.springaipractice.app.translation.dto.TranslateRespDto;
import com.kjung.springaipractice.app.translation.dto.TranslationTextReqDto;
import com.kjung.springaipractice.app.translation.service.TranslationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "번역")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/translation")
public class TranslationController {

    private final TranslationService translationService;


    @PostMapping
    public TranslateRespDto translate(@RequestBody TranslationTextReqDto param) {
        return translationService.translate(param);
    }
}
