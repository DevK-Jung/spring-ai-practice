package com.kjung.springaipractice.app.chat.service;

import com.kjung.springaipractice.app.chat.dto.ChatReqDto;
import com.kjung.springaipractice.app.chat.dto.ChatRespDto;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface ChatService {
    ChatRespDto chat(ChatReqDto param);

    List<Message> getHistory(String conversationId);

    void clearHistory(String conversationId);
}
