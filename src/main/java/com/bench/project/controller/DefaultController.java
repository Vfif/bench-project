package com.bench.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.bench.project.config.RabbitConfiguration.DEFAULT_ROUTING_KEY;

@RestController
@EnableRabbit
@Slf4j
@RequiredArgsConstructor
public class DefaultController {

    @Autowired
    private final RabbitTemplate template;

    @Autowired
    private final TopicExchange exchange;

    @GetMapping("/hello/{message}")
    public ResponseEntity<String> hello(@PathVariable String message) {
        log.info("Receive message to default queue: " + message);
        template.convertAndSend(exchange.getName(), DEFAULT_ROUTING_KEY, message);
        return ResponseEntity.ok("200 OK");
    }
}
