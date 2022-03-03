package com.bench.project.service.domain;

import java.io.Serializable;

public record CountKeyWordsRequest(
    String text,
    String keyWord
) implements Serializable {
}
