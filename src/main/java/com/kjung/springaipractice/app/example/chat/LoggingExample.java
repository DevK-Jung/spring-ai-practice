package com.kjung.springaipractice.app.example.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/example/logging")
public class LoggingExample {

    private final ChatClient chatClient;

    @GetMapping("/logging-advisor")
    public ChatResponse loggingAdvisor() {

        SimpleLoggerAdvisor customLogger = new SimpleLoggerAdvisor(
                request -> "Custom request: " + request.context(),
                response -> "Custom response: " + response.getResult(),
                0
        );

        return this.chatClient.prompt()
//                .advisors(new SimpleLoggerAdvisor())
                .advisors(customLogger)
                .user("Tell me a joke?")
                .call()
                .chatResponse();
    }
}
