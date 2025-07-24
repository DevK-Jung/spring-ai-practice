package com.kjung.springaipractice.app.chat.service.impl;

import com.kjung.springaipractice.app.chat.dto.ChatReqDto;
import com.kjung.springaipractice.app.chat.dto.ChatRespDto;
import com.kjung.springaipractice.app.chat.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatClient memoryChatClient;     // 대화용 (메모리 있음)

    private final ChatMemory chatMemory;

    public ChatServiceImpl(@Qualifier("memoryChatClient") ChatClient memoryChatClient,
                           ChatMemory chatMemory) {
        this.memoryChatClient = memoryChatClient;
        this.chatMemory = chatMemory;
    }

    @Override
    public ChatRespDto chat(ChatReqDto param) {
        String conversationId = param.getConversationId();

        // conversationId가 없으면 자동 생성 (새로운 변수에 할당)
        final String finalConversationId = (conversationId == null || conversationId.isEmpty())
                ? generateConversationId()
                : conversationId;

        String content = memoryChatClient.prompt(param.getText())
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, finalConversationId))
                .call()
                .content();

        return new ChatRespDto(content, finalConversationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getHistory(String conversationId) {
        return chatMemory.get(conversationId);
    }

    @Override
    public void clearHistory(String conversationId) {
        chatMemory.clear(conversationId);
    }

    private String generateConversationId() {
        return "conv_" + System.currentTimeMillis() + "_" +
                java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}
