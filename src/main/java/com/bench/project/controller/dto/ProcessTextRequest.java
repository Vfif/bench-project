package com.bench.project.controller.dto;

import java.util.List;

public record ProcessTextRequest(
    List<String> operations,
    String text,
    String keyword
) {
}
