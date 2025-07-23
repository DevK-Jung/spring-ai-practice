package com.kjung.springaipractice.core.prompt.mapper;

import com.kjung.springaipractice.core.prompt.annotation.PromptVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Component
public class PromptVariableMapper {

    public Map<String, Object> toMap(Object dto) {
        Map<String, Object> variables = new HashMap<>();

        List<Field> allFields = getAllFields(dto.getClass());

        for (Field field : allFields) {
            PromptVariable annotation = field.getAnnotation(PromptVariable.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(dto);

                    if (value != null) {
                        String key = annotation.value().isEmpty() ?
                                field.getName() : annotation.value();
                        variables.put(key, value);

                        log.debug("프롬프트 변수 매핑: {} = {}", key, value);
                    }
                } catch (Exception e) {
                    log.error("필드 접근 오류 - 클래스: {}, 필드: {}",
                            dto.getClass().getSimpleName(), field.getName(), e);
                    throw new RuntimeException("필드 접근 오류: " + field.getName(), e);
                }
            }
        }

        log.debug("매핑된 변수 개수: {}", variables.size());
        return variables;
    }

    /**
     * 클래스 계층구조를 따라 올라가면서 모든 필드를 수집
     */
    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }
}
