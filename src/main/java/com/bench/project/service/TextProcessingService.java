package com.bench.project.service;

import com.bench.project.controller.dto.ProcessTextRequest;
import com.bench.project.service.domain.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bench.project.config.RabbitConfiguration.EXCHANGE;

@Service
@RequiredArgsConstructor
public class TextProcessingService {

    @Autowired
    private final RabbitTemplate template;

    public void process(ProcessTextRequest request) {

        request.operations().forEach(
            operation -> {
                switch (operation) {
                    case COUNT_KEYWORDS_ROUTING_KEY, COUNT_WORDS_ROUTING_KEY -> template.convertAndSend(EXCHANGE, operation, message);
                    case null ->  log.warn("Null operation not supported");
                    case default ->  log.warn("Operation not supported: " + operation);
                }
            }
        );

        val message = new TextMessage(request.text(), request.extraInfo());
        String operationsString = String.join(".", request.operations());
        template.convertAndSend(operationsString, message);
    }
}
