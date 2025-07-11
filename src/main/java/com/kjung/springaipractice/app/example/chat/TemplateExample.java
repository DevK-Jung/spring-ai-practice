package com.kjung.springaipractice.app.example.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/example/template")
public class TemplateExample {

    private final ChatClient chatClient;

    @GetMapping
    public String template(String param) {
        return chatClient.prompt()
                .user(u -> u
                        .text("Tell me the names of 5 movies whose soundtrack was composed by {composer}")
                        .param("composer", param))
                .call()
                .content();
    }

    /**
     * 다른 템플릿 엔진을 사용하려면 TemplateRendererChatClient에 직접 사용자 지정 인터페이스 구현을 제공할 수 있습니다. 기본 템플릿을 StTemplateRenderer그대로 사용하되 사용자 지정 구성을 적용할 수도 있습니다.
     * <p>
     * 예를 들어, 기본적으로 템플릿 변수는 {}구문으로 식별됩니다. 프롬프트에 JSON을 포함하려는 경우 JSON 구문과의 충돌을 피하기 위해 다른 구문을 사용하는 것이 좋습니다. 예를 들어, <및 >구분 기호를 사용할 수 있습니다.
     *
     * @param param
     * @return
     */
    @GetMapping("template-renderer")
    public String templateRenderer(String param) {
        return chatClient.prompt()
                .user(u -> u
                        .text("Tell me the names of 5 movies whose soundtrack was composed by <composer>")
                        .param("composer", param))
                .templateRenderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .call()
                .content();
    }


}
