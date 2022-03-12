package com.bench.project.controller;

import com.bench.project.controller.dto.ProcessText;
import com.bench.project.controller.dto.ProcessTextRequest;
import com.bench.project.service.TextProcessingService;
import com.bench.project.service.domain.LogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TextProcessingController {

    private final TextProcessingService service;

    @PostMapping("/text")
    public ResponseEntity<String> processText(@Valid @RequestBody ProcessTextRequest request) {

        ProcessText processText = ProcessText.fromRequest(UUID.randomUUID().toString(), request);

        log.info("Received message: " + processText);

        service.process(processText);

        return ResponseEntity.ok("200 OK");
    }

    @GetMapping("/results")
    public ResponseEntity<List<LogDto>> getProcessedText() {

        List<LogDto> results = service.getResults();

        return ResponseEntity.ok(results);
    }

    @GetMapping("/results/{requestId}")
    public ResponseEntity<List<LogDto>> getProcessedTextByRequestId(@PathVariable String requestId) {

        log.info("Received request id: " + requestId);

        List<LogDto> results = service.getResultsRequestId(requestId);

        return ResponseEntity.ok(results);
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<LogDto> getProcessedTextById(@PathVariable String id) {

        log.info("Received id: " + id);

        LogDto result = service.getResultsById(id);

        return ResponseEntity.ok(result);
    }
}
