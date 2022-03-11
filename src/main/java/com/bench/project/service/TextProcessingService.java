package com.bench.project.service;

import com.bench.project.controller.dto.ProcessTextRequest;
import com.bench.project.service.dao.LogDao;
import com.bench.project.service.domain.LogDto;
import com.bench.project.service.domain.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bench.project.config.OperationConstants.COUNT_KEYWORDS;
import static com.bench.project.config.OperationConstants.COUNT_WORDS;
import static com.bench.project.config.OperationConstants.RANDOM;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextProcessingService {

    private final RabbitTemplate template;
    private final LogDao dao;

    public void process(ProcessTextRequest request) {
        val message = new TextMessage(request.text(), request.extraInfo());

        request.operations().forEach(
            operation -> {
                switch (operation) {
                    case COUNT_WORDS, COUNT_KEYWORDS, RANDOM -> template.convertAndSend(operation, message);
                    case null -> log.warn("Null operation not supported");
                    case default -> log.warn("Operation not supported: " + operation);
                }
            }
        );
    }

    public List<LogDto> getResults() {
        return dao.getAll();
    }
}
