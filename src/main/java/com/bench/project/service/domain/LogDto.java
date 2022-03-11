package com.bench.project.service.domain;

import java.util.UUID;

public record LogDto(
        String id,
        String requestId,
        String operation,
        String keyWord,
        String text,
        String result
) {

    public static LogDto from(
            String requestId,
            String operation,
            String keyword,
            TextMessage message,
            String result
    ) {
        return new LogDto(UUID.randomUUID().toString(), requestId, operation, keyword, message.text(), result);
    }
}
