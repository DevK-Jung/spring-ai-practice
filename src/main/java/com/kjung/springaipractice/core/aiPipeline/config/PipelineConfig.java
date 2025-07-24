package com.kjung.springaipractice.core.aiPipeline.config;

import com.kjung.springaipractice.core.aiPipeline.constants.ResponseCallType;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import com.kjung.springaipractice.core.prompt.constants.PromptType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.Map;

@Getter
@Builder
public class PipelineConfig {
    private PromptCategory category;
    @Builder.Default
    private PromptType type = PromptType.DEFAULT;
    private Class<?> responseType;
    @Builder.Default
    private boolean useSystemPrompt = true;
    @Builder.Default
    private boolean useUserPrompt = true;
    private ChatOptions chatOptions;
    private Map<String, Object> extraVariables;
    @Builder.Default
    private ResponseCallType responseCallType = ResponseCallType.CONTENT;

    public static PipelineConfig forContent(PromptCategory category, PromptType type) {
        return PipelineConfig.builder()
                .category(category).type(type)
                .responseType(String.class)
                .useSystemPrompt(true).useUserPrompt(true)
                .responseCallType(ResponseCallType.CONTENT)
                .build();
    }

    public static PipelineConfig forEntity(PromptCategory category, PromptType type, Class<?> entityClass) {
        return PipelineConfig.builder()
                .category(category).type(type)
                .responseType(entityClass)
                .useSystemPrompt(true).useUserPrompt(true)
                .responseCallType(ResponseCallType.ENTITY)
                .build();
    }
}
