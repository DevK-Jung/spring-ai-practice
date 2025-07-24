package com.kjung.springaipractice.app.translation.service.impl;

import com.kjung.springaipractice.app.translation.dto.TranslateFileReqDto;
import com.kjung.springaipractice.app.translation.dto.TranslateReqDto;
import com.kjung.springaipractice.app.translation.dto.TranslateRespDto;
import com.kjung.springaipractice.app.translation.service.TranslationService;
import com.kjung.springaipractice.core.aiPipeline.service.GenericAiPipeline;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import com.kjung.springaipractice.infra.file.FileTextExtractorHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final GenericAiPipeline pipeline;

    private final FileTextExtractorHelper fileTextExtractorHelper;

    @Override
    public TranslateRespDto translate(TranslateReqDto param) {
        return pipeline.builder(PromptCategory.TRANSLATION)
                .pipelineRequest(param)
                .executeForEntity(TranslateRespDto.class);
    }

    @Override
    public TranslateRespDto translateFile(TranslateFileReqDto param) throws IOException {
        String text = fileTextExtractorHelper.extractTextFromFile(param.getFile());

        TranslateReqDto request = new TranslateReqDto(param.getTargetLang(), text);

        return translate(request);
    }

}
