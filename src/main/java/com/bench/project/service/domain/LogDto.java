package com.bench.project.service.domain;

import java.util.Map;
import java.util.UUID;

public record LogDto(
        String id,
        String requestId,
        String operation,
        Map<String, String> extraInfo,
        String text,
        String result
) {

    public static LogDto from(
            String requestId,
            String operation,
            Map<String, String> extraInfo,
            TextMessage message,
            String result
    ) {
        return new LogDto(UUID.randomUUID().toString(), requestId, operation, extraInfo, message.text(), result);
    }
}
