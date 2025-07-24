package com.kjung.springaipractice.app.chat.controller;

import com.kjung.springaipractice.app.chat.dto.ChatReqDto;
import com.kjung.springaipractice.app.chat.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "채팅")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public String chat(@Validated @RequestBody ChatReqDto param) {
        return chatService.chat(param);
    }
}
