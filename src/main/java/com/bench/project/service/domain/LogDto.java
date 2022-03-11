package com.bench.project.service.domain;

import com.bench.project.config.OperationConstants;

public record LogDto(
        String id,
        String operation,
        String keyWord,
        String text,
        String result
) {

    public static LogDto from(
            String id,
            String operation,
            String keyword,
            TextMessage message,
            String result
    ) {
        return new LogDto(id, operation, keyword, message.text(), result);
    }
}
