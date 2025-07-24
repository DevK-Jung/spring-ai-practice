package com.kjung.springaipractice.app.summary.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummaryRespDto {
    private Integer summaryLength;
    private String result;
}
