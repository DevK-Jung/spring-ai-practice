package com.kjung.springaipractice.core.prompt.manager;

import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import com.kjung.springaipractice.core.prompt.constants.PromptType;
import com.kjung.springaipractice.core.prompt.event.PromptReloadEvent;
import com.kjung.springaipractice.core.prompt.loader.PromptFileLoader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromptManager {

    private final PromptFileLoader promptFileLoader;

    private final Map<String, Map<String, Object>> promptCache = new ConcurrentHashMap<>();

    public static final String SYSTEM = "system";

    public static final String USER = "user";

    @PostConstruct
    public void initializePrompts() throws IOException {
        loadPromptsIntoCache();
    }

    private void loadPromptsIntoCache() throws IOException {
        promptCache.put(SYSTEM, promptFileLoader.loadPromptFile("system-prompts.yml"));
        promptCache.put(USER, promptFileLoader.loadPromptFile("user-prompts.yml"));

        log.info("프롬프트 캐시 초기화 완료 - System: {}, User: {}",
                promptCache.get(SYSTEM).size(),
                promptCache.get(USER).size());
    }

    public String getSystemPrompt(PromptCategory category, PromptType type) {
        Map<String, Object> systemPrompts = promptCache.get(SYSTEM);

        if (systemPrompts == null)
            throw new IllegalStateException("시스템 프롬프트가 로드되지 않았습니다.");

        @SuppressWarnings("unchecked")
        Map<String, String> categoryPrompts =
                (Map<String, String>) systemPrompts.get(category.getName());

        if (categoryPrompts == null)
            throw new IllegalArgumentException("시스템 프롬프트 카테고리를 찾을 수 없습니다: " + category);

        String prompt = categoryPrompts.get(type.getName());

        if (prompt == null) {
            prompt = categoryPrompts.get(PromptType.DEFAULT.getName());
            if (prompt == null) {
                throw new IllegalArgumentException(
                        String.format("시스템 프롬프트를 찾을 수 없습니다: %s.%s", category, type));
            }
        }

        return prompt.trim();
    }

    public String getUserPrompt(PromptCategory category,
                                PromptType type,
                                Map<String, Object> variables) {

        Map<String, Object> userPrompts = promptCache.get(USER);

        if (userPrompts == null)
            throw new IllegalStateException("사용자 프롬프트가 로드되지 않았습니다.");

        @SuppressWarnings("unchecked")
        Map<String, String> categoryPrompts = (Map<String, String>) userPrompts.get(category.getName());

        if (categoryPrompts == null) {
            throw new IllegalArgumentException("사용자 프롬프트 카테고리를 찾을 수 없습니다: " + category);
        }

        String template = categoryPrompts.get(type.getName());
        if (template == null) {
            template = categoryPrompts.get(PromptType.DEFAULT.getName());
            if (template == null) {
                throw new IllegalArgumentException(
                        String.format("사용자 프롬프트를 찾을 수 없습니다: %s.%s", category, type));
            }
        }

        return replaceTemplateVariables(template, variables);
    }

    private String replaceTemplateVariables(String template, Map<String, Object> variables) {

        return new PromptTemplate(template).render(variables);
    }

    @EventListener
    public void reloadPrompts(PromptReloadEvent event) throws IOException {
        log.info("프롬프트 파일 재로드 시작");
        loadPromptsIntoCache();
        log.info("프롬프트 파일 재로드 완료");
    }
}