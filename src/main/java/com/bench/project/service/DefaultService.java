package com.bench.project.service;

import com.bench.project.service.domain.CountKeyWordsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultService {

    @Autowired
    private final RabbitTemplate template;

    @Autowired
    private final TopicExchange exchange;

    public void process(String routingKey, CountKeyWordsRequest obj) {
        template.convertAndSend(exchange.getName(), routingKey, obj);
    }
}
