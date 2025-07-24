package com.kjung.springaipractice.app.language.service;

import com.kjung.springaipractice.app.language.dto.LanguageDetectionRespDto;

public interface LanguageService {
    LanguageDetectionRespDto languageDetect(String text);
}
