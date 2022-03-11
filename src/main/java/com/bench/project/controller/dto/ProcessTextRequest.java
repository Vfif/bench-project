package com.bench.project.controller.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Validated
public record ProcessTextRequest(
    @NotEmpty
    List<String> operations,
    @NotBlank
    String text,
    Map<String, String> extraInfo
) {
}
