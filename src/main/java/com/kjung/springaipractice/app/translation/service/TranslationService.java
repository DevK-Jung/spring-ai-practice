package com.kjung.springaipractice.app.translation.service;

import com.kjung.springaipractice.app.translation.dto.TranslateRespDto;
import com.kjung.springaipractice.app.translation.dto.TranslationTextReqDto;

public interface TranslationService {
    TranslateRespDto translate(TranslationTextReqDto param);
}
