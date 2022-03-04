package com.bench.project.service.domain;

import java.io.Serializable;

public record TextMessage(
    String text,
    String keyword
) implements Serializable {
}
