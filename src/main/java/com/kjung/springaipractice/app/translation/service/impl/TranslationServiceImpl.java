package com.kjung.springaipractice.app.translation.service.impl;

import com.kjung.springaipractice.app.translation.dto.TranslateRespDto;
import com.kjung.springaipractice.app.translation.dto.TranslationTextReqDto;
import com.kjung.springaipractice.app.translation.service.TranslationService;
import com.kjung.springaipractice.core.aiPipeline.service.GenericAiPipeline;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final GenericAiPipeline pipeline;

    @Override
    public TranslateRespDto translate(TranslationTextReqDto param) {
        return pipeline.builder(PromptCategory.TRANSLATION)
                .pipelineRequest(param)
                .executeForEntity(TranslateRespDto.class);
    }

}
