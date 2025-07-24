-- mysql 테이블생성(해당 쿼리는 직접 DB에서 실행해야함)
CREATE TABLE IF NOT EXISTS SPRING_AI_CHAT_MEMORY (
                                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                     conversation_id VARCHAR(36) NOT NULL,
    content TEXT NOT NULL,
    type ENUM('USER', 'ASSISTANT', 'SYSTEM', 'TOOL') NOT NULL,
    `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- 인덱스들
CREATE INDEX idx_conversation_timestamp
    ON SPRING_AI_CHAT_MEMORY(conversation_id, `timestamp` DESC);

CREATE INDEX idx_conversation_type
    ON SPRING_AI_CHAT_MEMORY(conversation_id, type);

-- 오래된 데이터 정리를 위한 인덱스
CREATE INDEX idx_timestamp
    ON SPRING_AI_CHAT_MEMORY(`timestamp`);