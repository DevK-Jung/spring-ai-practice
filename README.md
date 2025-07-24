# Spring AI Practice

## 프로젝트 소개

Spring AI를 활용한 다양한 AI 기능을 제공하는 실습 프로젝트입니다. 텍스트 번역, 요약, 언어 감지, 채팅 등의 AI 기반 서비스를 REST API로 제공합니다.

## 기술 스택

### Core Framework
- **Java 21**
- **Spring Boot 3.4.7**
- **Spring AI 1.0.0**

### AI & LLM
- **Ollama** (로컬 LLM 실행 환경)
- **Gemma 3:27B** (기본 모델)
- **Spring AI Chat Memory** (대화 기록 관리)

### Database
- **MySQL 8.0**
- **Spring Data JPA**
- **HikariCP** (Connection Pool)

### Documentation & File Processing
- **Apache POI** (Word, Excel 문서 처리)
- **Apache PDFBox** (PDF 문서 처리)
- **SpringDoc OpenAPI** (Swagger UI)

### Utilities
- **Lombok**
- **Jackson** (JSON 처리)

## 디렉토리 구조

```
src/main/java/com/kjung/springaipractice/
├── app/                          # 애플리케이션 레이어
│   ├── chat/                     # 채팅 기능
│   │   ├── controller/
│   │   ├── dto/
│   │   └── service/
│   ├── translation/              # 번역 기능
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── service/
│   │   └── constants/
│   ├── summary/                  # 요약 기능
│   │   ├── controller/
│   │   ├── dto/
│   │   └── service/
│   ├── language/                 # 언어 감지 기능
│   │   ├── controller/
│   │   ├── dto/
│   │   └── service/
│   └── example/                  # Spring AI 예제들
│       └── chat/
├── core/                         # 핵심 비즈니스 로직
│   ├── aiPipeline/              # AI 파이프라인 프레임워크
│   │   ├── config/
│   │   ├── constants/
│   │   ├── executor/            # PipelineExecutor
│   │   ├── marker/
│   │   └── service/             # GenericAiPipeline
│   ├── config/                  # 설정
│   └── prompt/                  # 프롬프트 관리 시스템
│       ├── annotation/
│       ├── constants/
│       ├── event/
│       ├── loader/
│       ├── manager/
│       └── mapper/
├── infra/                       # 인프라스트럭처
│   └── file/                    # 파일 처리
└── resources/
    ├── prompts/                 # YAML 프롬프트 파일들
    ├── ddl/                     # 데이터베이스 스키마
    └── application.yml
```

## 주요 기능

### 1. 텍스트 번역 (Translation)
- **텍스트 번역**: 다양한 언어 간 텍스트 번역
- **파일 번역**: PDF, Word, TXT 파일 내용 번역
- **지원 언어**: 한국어, 영어, 일본어, 중국어 등

**API 엔드포인트**:
```
POST /api/v1/translation        # 텍스트 번역
POST /api/v1/translation/file   # 파일 번역
```

### 2. 텍스트 요약 (Summary)
- **텍스트 요약**: 긴 텍스트의 핵심 내용 추출
- **파일 요약**: 문서 파일의 내용 요약
- **다양한 요약 스타일**: 간단 요약, 상세 요약 등

**API 엔드포인트**:
```
POST /api/v1/summary           # 텍스트 요약
POST /api/v1/summary/file      # 파일 요약
```

### 3. 언어 감지 (Language Detection)
- **자동 언어 감지**: 입력 텍스트의 언어 자동 감지
- **신뢰도 점수**: 감지 결과의 신뢰도 제공

**API 엔드포인트**:
```
POST /language/detect          # 언어 감지
```

### 4. AI 채팅 (Chat)
- **대화형 AI**: 자연어 대화 인터페이스
- **대화 기록 관리**: 세션별 대화 이력 저장/조회
- **컨텍스트 유지**: 이전 대화 내용을 기억하는 연속 대화

**API 엔드포인트**:
```
POST /api/v1/chat                    # 채팅
GET /api/v1/chat/{conversationId}    # 대화 기록 조회
DELETE /api/v1/chat/{conversationId} # 대화 기록 삭제
```

### 5. 파일 처리 지원
- **PDF**: 텍스트 추출 및 처리
- **Word**: .doc, .docx 파일 지원
- **텍스트**: .txt 파일 처리
- **최대 파일 크기**: 50MB

## 핵심 아키텍처

### AI Pipeline Framework
프로젝트의 핵심인 **GenericAiPipeline**과 **PipelineExecutor**를 통해 AI 요청을 표준화된 방식으로 처리합니다.

```java
// 사용 예시
String result = pipeline.builder(PromptCategory.TRANSLATION)
    .type(PromptType.DEFAULT)
    .variable("targetLang", "English")
    .variable("text", "안녕하세요")
    .executeForContent();
```

### 프롬프트 관리 시스템
- **YAML 기반**: 시스템/사용자 프롬프트를 YAML 파일로 관리
- **동적 변수**: 프롬프트 내 변수 치환 지원
- **카테고리별 관리**: 기능별로 프롬프트 분류 관리

---

**Note**: 이 프로젝트는 Spring AI 학습 및 실습을 목적으로 제작되었습니다.