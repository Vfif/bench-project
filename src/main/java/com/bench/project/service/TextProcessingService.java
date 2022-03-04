package com.bench.project.service;

import com.bench.project.controller.dto.ProcessTextRequest;
import com.bench.project.service.domain.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextProcessingService {

    @Autowired
    private final RabbitTemplate template;

    @Autowired
    private final TopicExchange exchange;

    public void process(ProcessTextRequest request) {

        val message = new TextMessage(request.text(), request.keyword());
        request.operations().forEach(
            it -> template.convertAndSend(exchange.getName(), it, message)
        );
    }
}
