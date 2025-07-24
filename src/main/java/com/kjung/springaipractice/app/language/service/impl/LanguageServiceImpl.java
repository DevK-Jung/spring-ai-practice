package com.kjung.springaipractice.app.language.service.impl;

import com.kjung.springaipractice.app.language.dto.LanguageDetectionRespDto;
import com.kjung.springaipractice.app.language.service.LanguageService;
import com.kjung.springaipractice.core.aiPipeline.service.GenericAiPipeline;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final GenericAiPipeline pipeline;

    @Override
    public LanguageDetectionRespDto languageDetect(String text) {
        String languageCode = pipeline.builder(PromptCategory.LANGUAGE_DETECTION)
                .variable("text", text)
                .executeForContent();

        return new LanguageDetectionRespDto(languageCode);
    }
}
