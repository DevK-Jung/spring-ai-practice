package com.kjung.springaipractice.app.summary.controller;

import com.kjung.springaipractice.app.summary.dto.SummaryFileReqDto;
import com.kjung.springaipractice.app.summary.dto.SummaryReqDto;
import com.kjung.springaipractice.app.summary.dto.SummaryRespDto;
import com.kjung.springaipractice.app.summary.service.SummaryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "요약")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/summary")
public class SummaryController {

    private final SummaryService summaryService;

    @PostMapping
    public SummaryRespDto summarize(@RequestBody SummaryReqDto param) {
        return summaryService.summarize(param);
    }

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SummaryRespDto summarizeFile(SummaryFileReqDto param) throws IOException {
        return summaryService.summarizeFile(param);
    }
}
