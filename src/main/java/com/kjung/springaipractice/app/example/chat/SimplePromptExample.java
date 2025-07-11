package com.kjung.springaipractice.app.example.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/example/simple")
public class SimplePromptExample {

    private final ChatClient chatClient;

    public SimplePromptExample(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/blocking-content")
    public ChatResponse blockingContent(String userInput) {

        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .chatResponse();
    }

    @GetMapping("/blocking-chat-resp")
    public ChatResponse blockingChatResp(String userInput) {

        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .chatResponse();
    }

    @GetMapping(value = "/streaming-content", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamingContent(String userInput) {

        return this.chatClient.prompt()
                .user(userInput)
                .stream()
                .content();
    }

    @GetMapping(value = "/streaming-chat-resp", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> streamingChatResp(String userInput) {

        return this.chatClient.prompt()
                .user(userInput)
                .stream()
                .chatResponse();
    }
}
