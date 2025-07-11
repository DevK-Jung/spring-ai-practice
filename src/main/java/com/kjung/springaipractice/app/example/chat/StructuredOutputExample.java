package com.kjung.springaipractice.app.example.chat;

import com.kjung.springaipractice.app.example.chat.dto.ActorsFilms;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/example/structured")
public class StructuredOutputExample {

    private final ChatClient chatClient;

    @GetMapping("/blocking-entity")
    public ActorsFilms blockingEntity() {

        return this.chatClient.prompt()
                .user("한국 배우 이름과 출연 영화를 알려줘")
                .call()
                .entity(ActorsFilms.class);
    }

    @GetMapping("/blocking-entities")
    public List<ActorsFilms> blockingEntities() {

        return this.chatClient.prompt()
                .user("한국 배우 이름과 출연 영화를 알려줘")
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
    }

    // 스트리밍 데이터 구조화된 출력 변환기
    @GetMapping(value = "/streaming-entities")
    public List<ActorsFilms> streamingEntities() {
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
