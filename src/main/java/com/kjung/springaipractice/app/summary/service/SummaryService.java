package com.kjung.springaipractice.app.summary.service;

import com.kjung.springaipractice.app.summary.dto.SummaryFileReqDto;
import com.kjung.springaipractice.app.summary.dto.SummaryReqDto;
import com.kjung.springaipractice.app.summary.dto.SummaryRespDto;

import java.io.IOException;

public interface SummaryService {
    SummaryRespDto summarize(SummaryReqDto param);

    SummaryRespDto summarizeFile(SummaryFileReqDto param) throws IOException;
}
