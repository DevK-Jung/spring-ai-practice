package com.kjung.springaipractice.app.sample.controller;

import com.kjung.springaipractice.app.sample.dto.ActorsFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

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
    public ActorsFilms entity() {

        return this.chatClient.prompt()
                .user("한국 배우 이름과 출연 영화를 알려줘")
                .call()
                .entity(ActorsFilms.class);
    }


    // entity 반환
    @GetMapping("/blocking-entities")
    public List<ActorsFilms> entities() {

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

    // 스트리밍 데이터 구조화된 출력 변환기
    @GetMapping(value = "/struct-streaming")
    public List<ActorsFilms> structStreaming() {
        var converter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<ActorsFilms>>() {
        });

        Flux<String> flux = this.chatClient.prompt()
                .user(u -> u.text("""
                                  Generate the filmography for a random actor.
                                  {format}
                                """)
                        .param("format", converter.getFormat()))
                .stream()
                .content();

        String content = flux.collectList()
                .block()
                .stream()
                .collect(Collectors.joining());

        return converter.convert(content);
    }

}
