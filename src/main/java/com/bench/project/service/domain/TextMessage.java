package com.bench.project.service.domain;

import java.io.Serializable;
import java.util.Map;

public record TextMessage(
    String id,
    String text,
    Map<String, String> extraInfo
) implements Serializable {
}
