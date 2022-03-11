package com.bench.project.service.domain;

import com.bench.project.config.OperationConstants;

public record LogDto(
        String operation,
        String keyWord,
        String text,
        String result
) {

    public static LogDto from(
            String operation,
            String keyword,
            TextMessage message,
            String result
    ) {
        return new LogDto(operation, keyword, message.text(), result);
    }
}
