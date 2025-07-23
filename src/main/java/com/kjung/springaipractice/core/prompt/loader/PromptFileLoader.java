package com.kjung.springaipractice.core.prompt.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class PromptFileLoader {

    private final ResourceLoader resourceLoader;

    private final ObjectMapper yamlMapper;

    public PromptFileLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
    }

    @PostConstruct
    public void validatePromptFiles() {
        String[] requiredFiles = {"system-prompts.yml", "user-prompts.yml"};

        for (String fileName : requiredFiles) {
            Resource resource = resourceLoader.getResource("classpath:prompts/" + fileName);
            if (!resource.exists()) {
                log.warn("필수 프롬프트 파일이 없습니다: {}", fileName);
            } else {
                log.info("프롬프트 파일 로드 완료: {}", fileName);
            }
        }
    }

    public Map<String, Object> loadPromptFile(String fileName) throws IOException {
        try {
            Resource resource = resourceLoader.getResource("classpath:prompts/" + fileName);

            if (!resource.exists())
                throw new IllegalStateException("프롬프트 파일이 존재하지 않습니다.");

            return yamlMapper.readValue(resource.getInputStream(), Map.class);

        } catch (IOException e) {
            log.error("프롬프트 파일 로딩 실패: {}", fileName, e);
            throw e;
        }
    }
}
