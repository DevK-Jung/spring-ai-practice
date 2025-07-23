package com.kjung.springaipractice.core.aiPipeline.executor;

import com.kjung.springaipractice.core.aiPipeline.config.PipelineConfig;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import com.kjung.springaipractice.core.prompt.constants.PromptType;
import com.kjung.springaipractice.core.aiPipeline.marker.PipelineRequest;
import com.kjung.springaipractice.core.aiPipeline.service.GenericAiPipeline;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.HashMap;
import java.util.Map;

public class PipelineExecutor {

    private final GenericAiPipeline pipeline;
    private final PipelineConfig.PipelineConfigBuilder configBuilder;
    private final Map<String, Object> variables;
    private PipelineRequest request;

    public PipelineExecutor(GenericAiPipeline pipeline) {
        this.pipeline = pipeline;
        this.configBuilder = PipelineConfig.builder();
        this.variables = new HashMap<>();
    }

    public PipelineExecutor category(PromptCategory category) {
        configBuilder.category(category);
        return this;
    }

    public PipelineExecutor type(PromptType type) {
        configBuilder.type(type);
        return this;
    }

    public PipelineExecutor responseType(Class<?> responseType) {
        configBuilder.responseType(responseType);
        return this;
    }

    public PipelineExecutor systemPrompt(boolean use) {
        configBuilder.useSystemPrompt(use);
        return this;
    }

    public PipelineExecutor userPrompt(boolean use) {
        configBuilder.useUserPrompt(use);
        return this;
    }

    public PipelineExecutor withDto(PipelineRequest request) {
        this.request = request;
        return this;
    }

    public PipelineExecutor variable(String key, Object value) {
        variables.put(key, value);
        return this;
    }

    public PipelineExecutor variables(Map<String, Object> vars) {
        variables.putAll(vars);
        return this;
    }

    public PipelineExecutor chatOptions(ChatOptions options) {
        configBuilder.chatOptions(options);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T execute() {
        PipelineConfig config = configBuilder
                .extraVariables(variables)
                .build();

        if (request != null) {
            return pipeline.execute(request, config);
        } else {
            return pipeline.execute(variables, config);
        }
    }
}