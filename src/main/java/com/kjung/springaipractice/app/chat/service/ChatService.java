package com.kjung.springaipractice.app.chat.service;

import com.kjung.springaipractice.app.chat.dto.ChatReqDto;

public interface ChatService {
    String chat(ChatReqDto param);
}
