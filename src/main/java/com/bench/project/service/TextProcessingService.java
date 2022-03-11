package com.bench.project.service;

import com.bench.project.controller.dto.ProcessTextRequest;
import com.bench.project.service.domain.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bench.project.config.OperationConstants;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextProcessingService {

    @Autowired
    private final RabbitTemplate template;

    public void process(ProcessTextRequest request) {
        val message = new TextMessage(request.text(), request.extraInfo());

        request.operations().forEach(
            operation -> {
                switch (operation) {
                    case String s && OperationConstants.list.contains(s) -> template.convertAndSend(operation, message);
                    case null -> log.warn("Null operation not supported");
                    case default -> log.warn("Operation not supported: " + operation);
                }
            }
        );
    }
}
