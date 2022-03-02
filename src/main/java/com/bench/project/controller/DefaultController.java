package com.bench.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.bench.project.config.RabbitConfiguration.queueName;

@RestController
@EnableRabbit
public class DefaultController {

    @RabbitListener(queues = queueName)
    public void processQueue(String message) {
        logger.info("New message " + message);
    }

    private final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    private final AmqpTemplate template;

    @Autowired
    public DefaultController(AmqpTemplate template) {
        this.template = template;
    }

    @GetMapping("/hello/{message}")
    public ResponseEntity<String> hello(@PathVariable String message) {
        logger.info("Receive message: " + message);
        template.convertAndSend(queueName, message);
        return ResponseEntity.ok("200 OK");
    }
}
