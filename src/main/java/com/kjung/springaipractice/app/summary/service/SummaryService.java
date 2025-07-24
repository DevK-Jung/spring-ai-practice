package com.kjung.springaipractice.app.summary.service;

import com.kjung.springaipractice.app.summary.dto.SummaryReqDto;
import com.kjung.springaipractice.app.summary.dto.SummaryRespDto;

public interface SummaryService {
    SummaryRespDto summarize(SummaryReqDto param);
}
