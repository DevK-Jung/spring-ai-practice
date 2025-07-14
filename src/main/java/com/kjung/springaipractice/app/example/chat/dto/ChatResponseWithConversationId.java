package com.kjung.springaipractice.app.example.chat.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;

@Getter
@RequiredArgsConstructor
public class ChatResponseWithConversationId {
    private final ChatResponse chatResponse;
    private final String conversationId;
}