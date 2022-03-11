package com.bench.project.controller;

import com.bench.project.controller.dto.ProcessText;
import com.bench.project.controller.dto.ProcessTextRequest;
import com.bench.project.service.TextProcessingService;
import com.bench.project.service.domain.LogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TextProcessingController {

    @Autowired
    private final TextProcessingService service;

    @PostMapping("/text")
    public ResponseEntity<String> processText(@Valid @RequestBody ProcessTextRequest request) {

        ProcessText processText = ProcessText.fromRequest(request);

        log.info("Received message: " + processText);

        service.process(processText);

        return ResponseEntity.ok("200 OK");
    }

    @GetMapping("/results")
    public ResponseEntity<List<LogDto>> getProcessedText() {

        List<LogDto> results = service.getResults();

        return ResponseEntity.ok(results);
    }

    @GetMapping("/results/{id}")
    public ResponseEntity<List<LogDto>> getProcessedTextById(@PathVariable String id) {

        List<LogDto> results = service.getResults(id);

        return ResponseEntity.ok(results);
    }
}
