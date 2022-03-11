package com.bench.project.controller.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ProcessText(

    String requestId,

    List<String> operations,

    String text,

    Map<String, String> extraInfo
) {

    public static ProcessText fromRequest(String requestId, ProcessTextRequest req) {
        return new ProcessText(
                requestId,
                req.operations(),
                req.text(),
                req.extraInfo()
        );
    }
}
