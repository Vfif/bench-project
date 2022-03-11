package com.bench.project.service;

import com.bench.project.controller.dto.ProcessText;
import com.bench.project.service.dao.LogDao;
import com.bench.project.service.domain.LogDto;
import com.bench.project.service.domain.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.bench.project.config.OperationConstants;
import java.util.List;

import static com.bench.project.config.OperationConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextProcessingService {

    private final RabbitTemplate template;
    private final LogDao dao;

    public void process(ProcessText request) {
        val message = new TextMessage(request.requestId(), request.text(), request.extraInfo());

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

    public List<LogDto> getResults() {
        return dao.getAll();
    }

    public List<LogDto> getResultsRequestId(String id) {
        return dao.getByRequestId(id);
    }

    public LogDto getResultsById(String id) {
        return dao.getById(id);
    }
}
