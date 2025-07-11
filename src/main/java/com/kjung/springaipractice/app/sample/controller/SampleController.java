package com.kjung.springaipractice.app.sample.controller;

import com.kjung.springaipractice.app.sample.dto.ActorFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sample")
public class SampleController {

    private final ChatClient chatClient;

    public SampleController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/blocking")
    public ChatResponse blocking(String userInput) {

        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .chatResponse();
    }

    // entity 반환
    @GetMapping("/blocking-entity")
    public ActorFilms entity() {

        return this.chatClient.prompt()
                .user("한국 배우 이름과 출연 영화를 알려줘")
                .call()
                .entity(ActorFilms.class);
    }


    // entity 반환
    @GetMapping("/blocking-entities")
    public List<ActorFilms> entities() {

        return this.chatClient.prompt()
                .user("한국 배우 이름과 출연 영화를 알려줘")
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
    }

    @GetMapping(value = "/streaming", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> streaming(String userInput) {

        return this.chatClient.prompt()
                .user(userInput)
                .stream()
                .chatResponse();
    }

}
