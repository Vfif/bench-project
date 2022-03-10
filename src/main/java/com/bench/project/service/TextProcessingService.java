package com.bench.project.service;

import com.bench.project.controller.dto.ProcessTextRequest;
import com.bench.project.service.domain.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextProcessingService {

    @Autowired
    private final RabbitTemplate template;

    public void process(ProcessTextRequest request) {

        val message = new TextMessage(request.text(), request.extraInfo());
        String operationsString = String.join(".", request.operations());
        template.convertAndSend(operationsString, message);
    }
}
