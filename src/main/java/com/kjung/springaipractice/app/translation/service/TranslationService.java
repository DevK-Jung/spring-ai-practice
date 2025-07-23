package com.kjung.springaipractice.app.translation.service;

import com.kjung.springaipractice.app.translation.dto.TranslationTextReqDto;

public interface TranslationService {
    String translate(TranslationTextReqDto param);
}
