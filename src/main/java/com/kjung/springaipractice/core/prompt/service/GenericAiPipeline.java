package com.kjung.springaipractice.core.prompt.service;

import com.kjung.springaipractice.core.prompt.config.PipelineConfig;
import com.kjung.springaipractice.core.prompt.constants.ResponseCallType;
import com.kjung.springaipractice.core.prompt.executor.PipelineExecutor;
import com.kjung.springaipractice.core.prompt.manager.PromptManager;
import com.kjung.springaipractice.core.prompt.mapper.PromptVariableMapper;
import com.kjung.springaipractice.core.prompt.marker.PipelineRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenericAiPipeline {

    private final ChatClient chatClient;

    private final PromptManager promptManager;

    private final PromptVariableMapper variableMapper;

    public <T> T execute(PipelineRequest request, PipelineConfig config) {
        if (isStreamingResponse(config.getResponseCallType()))
            throw new IllegalArgumentException("스트리밍 응답에는 executeStreaming()을 사용하세요.");

        return executeInternal(request, config);
    }

    public <T> T execute(Map<String, Object> variables, PipelineConfig config) {
        if (isStreamingResponse(config.getResponseCallType()))
            throw new IllegalArgumentException("스트리밍 응답에는 executeStreaming()을 사용하세요.");

        return executeInternal(variables, config);
    }

    @SuppressWarnings("unchecked")
    private <T> T executeInternal(Object requestOrVariables, PipelineConfig config) {
        try {
            // 변수 및 프롬프트 준비
            Map<String, Object> variables;
            if (requestOrVariables instanceof PipelineRequest) {
                variables = prepareVariables((PipelineRequest) requestOrVariables, config);
            } else {
                variables = mergeVariables((Map<String, Object>) requestOrVariables, config.getExtraVariables());
            }

            String systemPrompt = config.isUseSystemPrompt() ?
                    promptManager.getSystemPrompt(config.getCategory(), config.getType()) : null;
            String userPrompt = config.isUseUserPrompt() ?
                    promptManager.getUserPrompt(config.getCategory(), config.getType(), variables) : null;

            T result = callBlockingAI(systemPrompt, userPrompt, config);
            log.debug("파이프라인 실행 완료");
            return result;

        } catch (Exception e) {
            log.error("파이프라인 실행 실패", e);
            throw new RuntimeException("파이프라인 실행 실패", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T callBlockingAI(String systemPrompt, String userPrompt, PipelineConfig config) {
        validatePrompts(systemPrompt, userPrompt, config);
        var chatClientCall = buildChatClientCall(systemPrompt, userPrompt, config);

        try {
            return switch (config.getResponseCallType()) {
                case CONTENT -> {
                    log.debug("Content 응답 호출");
                    String content = chatClientCall.call().content();
                    yield (T) content;
                }

                case ENTITY -> {
                    log.debug("Entity 응답 호출 - 타입: {}", config.getResponseType().getSimpleName());
                    Object entity = chatClientCall.call().entity(config.getResponseType());
                    yield (T) entity;
                }

                case CHAT_RESPONSE -> {
                    log.debug("ChatResponse 응답 호출");
                    ChatResponse chatResponse = chatClientCall.call().chatResponse();
                    yield (T) chatResponse;
                }

                case RESPONSE_ENTITY -> {
                    log.debug("ResponseEntity 응답 호출 - 타입: {}", config.getResponseType().getSimpleName());
                    ResponseEntity<ChatResponse, ?> responseEntity = chatClientCall.call().responseEntity(config.getResponseType());
                    yield (T) responseEntity;
                }

                default -> {
                    log.warn("지원하지 않는 응답 타입, Content로 대체: {}", config.getResponseCallType());
                    String content = chatClientCall.call().content();
                    yield (T) content;
                }
            };

        } catch (Exception e) {
            log.error("AI 호출 실패 - 응답타입: {}", config.getResponseCallType(), e);
            throw new RuntimeException("AI 호출 실패: " + e.getMessage(), e);
        }
    }

    private void validatePrompts(String systemPrompt, String userPrompt, PipelineConfig config) {
        boolean hasValidSystemPrompt = config.isUseSystemPrompt() && hasContent(systemPrompt);
        boolean hasValidUserPrompt = config.isUseUserPrompt() && hasContent(userPrompt);

        if (!hasValidSystemPrompt && !hasValidUserPrompt) {
            throw new IllegalArgumentException("사용 가능한 프롬프트가 없습니다.");
        }
    }

    private boolean hasContent(String text) {
        return text != null && !text.trim().isEmpty();
    }

    private boolean isStreamingResponse(ResponseCallType responseCallType) {
        return responseCallType == ResponseCallType.STREAM_CONTENT ||
                responseCallType == ResponseCallType.STREAM_CHAT_RESPONSE;
    }

    private Map<String, Object> mergeVariables(Map<String, Object> primary, Map<String, Object> extra) {
        Map<String, Object> merged = new HashMap<>();
        if (primary != null) merged.putAll(primary);
        if (extra != null) merged.putAll(extra);
        return merged;
    }

    public PipelineExecutor builder() {
        return new PipelineExecutor(this);
    }

    private ChatClient.ChatClientRequestSpec buildChatClientCall(String systemPrompt, String userPrompt, PipelineConfig config) {
        var chatClientCall = chatClient.prompt();

        // 시스템 프롬프트 추가
        if (config.isUseSystemPrompt() && systemPrompt != null && !systemPrompt.trim().isEmpty()) {
            chatClientCall = chatClientCall.system(systemPrompt);
        }

        // 사용자 프롬프트 추가
        if (config.isUseUserPrompt() && userPrompt != null && !userPrompt.trim().isEmpty()) {
            chatClientCall = chatClientCall.user(userPrompt);
        }

        // 옵션 추가
        if (config.getChatOptions() != null) {
            chatClientCall = chatClientCall.options(config.getChatOptions());
        }

        return chatClientCall;
    }


    private Map<String, Object> prepareVariables(PipelineRequest request, PipelineConfig config) {
        Map<String, Object> variables = new HashMap<>();

        // DTO에서 변수 추출
        if (request != null) {
            variables.putAll(variableMapper.toMap(request));
        }

        // 추가 변수 병합
        if (!config.getExtraVariables().isEmpty()) {
            variables.putAll(config.getExtraVariables());
        }

        return variables;
    }
}
