package com.bench.project.controller;

import com.bench.project.controller.dto.ProcessTextRequest;
import com.bench.project.service.TextProcessingService;
import com.bench.project.service.domain.LogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TextProcessingController {

    @Autowired
    private final TextProcessingService service;

    @PostMapping("/text")
    public ResponseEntity<String> processText(@RequestBody ProcessTextRequest request) {

        log.info("Received message: " + request);

        service.process(request);

        return ResponseEntity.ok("200 OK");
    }

    @GetMapping("/results")
    public ResponseEntity<List<LogDto>> getProcessedText() {

        List<LogDto> results = service.getResults();

        return ResponseEntity.ok(results);
    }
}
