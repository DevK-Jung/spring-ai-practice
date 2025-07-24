package com.kjung.springaipractice.core.aiPipeline.executor;

import com.kjung.springaipractice.core.aiPipeline.config.PipelineConfig;
import com.kjung.springaipractice.core.aiPipeline.constants.ResponseCallType;
import com.kjung.springaipractice.core.aiPipeline.marker.PipelineRequest;
import com.kjung.springaipractice.core.aiPipeline.service.GenericAiPipeline;
import com.kjung.springaipractice.core.prompt.constants.PromptCategory;
import com.kjung.springaipractice.core.prompt.constants.PromptType;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.HashMap;
import java.util.Map;

public class PipelineExecutor {

    private final GenericAiPipeline pipeline;
    private final PipelineConfig.PipelineConfigBuilder configBuilder;
    private final Map<String, Object> variables;
    private PipelineRequest request;

    public PipelineExecutor(GenericAiPipeline pipeline, PromptCategory category) {
        this.pipeline = pipeline;
        this.configBuilder = PipelineConfig.builder().category(category);
        this.variables = new HashMap<>();
    }

    public PipelineExecutor category(PromptCategory category) {
        configBuilder.category(category);
        return this;
    }

    // deafult : PromptType.DEFAULT
    public PipelineExecutor type(PromptType type) {
        configBuilder.type(type);
        return this;
    }

//    public PipelineExecutor responseType(Class<?> responseType) {
//        configBuilder.responseType(responseType);
//        return this;
//    }

    // default: true
    public PipelineExecutor systemPrompt(boolean use) {
        configBuilder.useSystemPrompt(use);
        return this;
    }

    // default: true
    public PipelineExecutor userPrompt(boolean use) {
        configBuilder.useUserPrompt(use);
        return this;
    }

    public PipelineExecutor pipelineRequest(PipelineRequest request) {
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

    public String executeForContent() {
        PipelineConfig config = configBuilder
                .responseCallType(ResponseCallType.CONTENT)
                .extraVariables(variables)
                .build();

        if (request != null) {
            return pipeline.execute(request, config);
        } else {
            return pipeline.execute(variables, config);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T executeForEntity(Class<T> entityType) {
        PipelineConfig config = configBuilder
                .responseCallType(ResponseCallType.ENTITY)
                .responseType(entityType)
                .extraVariables(variables)
                .build();

        if (request != null) {
            return pipeline.execute(request, config);
        } else {
            return pipeline.execute(variables, config);
        }
    }

    public ChatResponse executeForChatResponse() {
        PipelineConfig config = configBuilder
                .responseCallType(ResponseCallType.CHAT_RESPONSE)
                .extraVariables(variables)
                .build();

        if (request != null) {
            return pipeline.execute(request, config);
        } else {
            return pipeline.execute(variables, config);
        }
    }

}