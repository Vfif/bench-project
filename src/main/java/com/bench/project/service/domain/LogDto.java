package com.bench.project.service.domain;

public record LogDto(
    String operation,
    String keyWord,
    String text,
    String result
) {
}
