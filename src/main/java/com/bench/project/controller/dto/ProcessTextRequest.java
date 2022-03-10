package com.bench.project.controller.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Validated
public record ProcessTextRequest(
    @NotBlank
    @NotNull
    List<String> operations,
    @NotBlank
    @NotNull
    String text,
    Map<String, String> extraInfo
) {
}
