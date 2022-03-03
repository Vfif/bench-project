package com.bench.project.controller;

import com.bench.project.service.DefaultService;
import com.bench.project.service.domain.CountKeyWordsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DefaultController {

    @Autowired
    private final DefaultService service;

    @PostMapping("/text")
    public ResponseEntity<String> precess(@RequestBody Map<String, String> map) {
        log.info("Receive message: " + map);

        CountKeyWordsRequest obj = buildRequest(map);
        service.process(map.get("operations"), obj);

        return ResponseEntity.ok("200 OK");
    }

    private CountKeyWordsRequest buildRequest(Map<String, String> map) {
        return new CountKeyWordsRequest(map.get("text"), map.get("keyWord"));
    }
}
