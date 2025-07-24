package com.kjung.springaipractice.app.translation.service;

import com.kjung.springaipractice.app.translation.dto.TranslateFileReqDto;
import com.kjung.springaipractice.app.translation.dto.TranslateRespDto;
import com.kjung.springaipractice.app.translation.dto.TranslateReqDto;

import java.io.IOException;

public interface TranslationService {
    TranslateRespDto translate(TranslateReqDto param);

    TranslateRespDto translateFile(TranslateFileReqDto param) throws IOException;
}
