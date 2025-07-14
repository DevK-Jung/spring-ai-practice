package com.kjung.springaipractice.app.example.chat;

import com.kjung.springaipractice.app.example.chat.dto.ChatResponseWithConversationId;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat-memory")
public class ChatMemoryExample {

    private final ChatClient chatClient;

    public ChatMemoryExample(ChatModel chatModel) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();

        SimpleLoggerAdvisor customLogger = new SimpleLoggerAdvisor(
                request -> "Custom request: " + request.context(),
                response -> "Custom response: " + response.getResult(),
                0
        );

        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        customLogger
                )
                .build();
    }

    @GetMapping
    public ChatResponseWithConversationId chatMemory(@RequestParam String input,
                                                     @RequestParam(required = false) String conversationId) {

        // conversationId가 없으면 자동 생성 (새로운 변수에 할당)
        final String finalConversationId = (conversationId == null || conversationId.isEmpty())
                ? generateConversationId()
                : conversationId;

        ChatResponse chatResponse = chatClient.prompt()
                .user(input)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, finalConversationId))
                .call()
                .chatResponse();

        return new ChatResponseWithConversationId(chatResponse, finalConversationId);
    }

    private String generateConversationId() {
        return "conv_" + System.currentTimeMillis() + "_" +
                java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}

