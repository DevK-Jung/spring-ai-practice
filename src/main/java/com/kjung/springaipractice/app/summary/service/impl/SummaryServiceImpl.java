
package com.kjung.springaipractice.app.summary.service.impl;

import com.kjung.springaipractice.app.summary.dto.SummaryFileReqDto;
import com.kjung.springaipractice.app.summary.dto.SummaryReqDto;
import com.kjung.springaipractice.app.summary.dto.SummaryRespDto;
import com.kjung.springaipractice.app.summary.service.SummaryService;
import com.kjung.springaipractice.core.aiPipeline.service.GenericAiPipeline;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import com.kjung.springaipractice.infra.file.FileTextExtractorHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final GenericAiPipeline pipeline;

    private final FileTextExtractorHelper fileTextExtractorHelper;

    @Override
    public SummaryRespDto summarize(SummaryReqDto param) {
        return pipeline.builder(PromptCategory.SUMMARIZATION)
                .pipelineRequest(param)
                .executeForEntity(SummaryRespDto.class);
    }

    @Override
    public SummaryRespDto summarizeFile(SummaryFileReqDto param) throws IOException {
        String text = fileTextExtractorHelper.extractTextFromFile(param.getFile());

        SummaryReqDto request = new SummaryReqDto(param.getSummaryLength(), text);

        return summarize(request);
    }
}
