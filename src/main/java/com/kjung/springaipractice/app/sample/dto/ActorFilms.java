package com.kjung.springaipractice.app.sample.dto;

import java.util.List;

public record ActorFilms(String actor, List<String> movies) {
}

