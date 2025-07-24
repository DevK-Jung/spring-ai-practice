
package com.kjung.springaipractice.app.summary.service.impl;

import com.kjung.springaipractice.app.summary.dto.SummaryReqDto;
import com.kjung.springaipractice.app.summary.dto.SummaryRespDto;
import com.kjung.springaipractice.app.summary.service.SummaryService;
import com.kjung.springaipractice.core.aiPipeline.service.GenericAiPipeline;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final GenericAiPipeline pipeline;

    @Override
    public SummaryRespDto summarize(SummaryReqDto param) {
        return pipeline.builder(PromptCategory.SUMMARIZATION)
                .pipelineRequest(param)
                .executeForEntity(SummaryRespDto.class);
    }
}
