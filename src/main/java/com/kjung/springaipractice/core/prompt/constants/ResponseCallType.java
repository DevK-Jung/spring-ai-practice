package com.kjung.springaipractice.core.prompt.constants;

public enum ResponseCallType {
    CONTENT,           // call().content() - String
    ENTITY,            // call().entity(Class) - 구조화된 객체
    CHAT_RESPONSE,     // call().chatResponse() - ChatResponse 전체
    RESPONSE_ENTITY,   // call().responseEntity() - ResponseEntity 형태

    STREAM_CONTENT,    // stream().content() - Flux<String>
    STREAM_CHAT_RESPONSE  // stream().chatResponse() - Flux<ChatResponse>
}
