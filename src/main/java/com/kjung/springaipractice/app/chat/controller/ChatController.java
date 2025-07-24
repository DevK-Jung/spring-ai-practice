package com.kjung.springaipractice.app.chat.controller;

import com.kjung.springaipractice.app.chat.dto.ChatReqDto;
import com.kjung.springaipractice.app.chat.dto.ChatRespDto;
import com.kjung.springaipractice.app.chat.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "채팅")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRespDto chat(@Validated @RequestBody ChatReqDto param) {
        return chatService.chat(param);
    }

    @GetMapping("/{conversationId}")
    public List<Message> getHistory(@PathVariable String conversationId) {
        return chatService.getHistory(conversationId);
    }

    @DeleteMapping("/{conversationId}")
    public void clearHistory(@PathVariable String conversationId) {
        chatService.clearHistory(conversationId);
    }
}
