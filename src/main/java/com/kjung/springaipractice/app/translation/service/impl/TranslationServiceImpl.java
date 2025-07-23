package com.kjung.springaipractice.app.translation.service.impl;

import com.kjung.springaipractice.app.translation.dto.TranslationTextReqDto;
import com.kjung.springaipractice.app.translation.service.TranslationService;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import com.kjung.springaipractice.core.prompt.constants.PromptType;
import com.kjung.springaipractice.core.prompt.service.GenericAiPipeline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final GenericAiPipeline pipeline;

    @Override
    public String translate(TranslationTextReqDto param) {

        return pipeline.builder()
                .category(PromptCategory.TRANSLATION)
                .type(PromptType.DEFAULT)
                .withDto(param)
                .execute();
    }

}
