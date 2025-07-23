package com.kjung.springaipractice.core.prompt.event;

import org.springframework.context.ApplicationEvent;

public class PromptReloadEvent extends ApplicationEvent {
    public PromptReloadEvent(Object source) {
        super(source);
    }
}