package com.bench.project.config.listener;

import com.bench.project.service.domain.TextMessage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.bench.project.config.OperationConstants.COUNT_KEYWORDS;
import static com.bench.project.config.OperationConstants.COUNT_WORDS;

@Slf4j
@Component
@EnableRabbit
public class RabbitListenersConfiguration {

    @RabbitListener(queues = COUNT_WORDS)
    public void countWords(TextMessage obj) {
        val wordsList = obj.text().split(" ");
        log.info("Words count = " + wordsList.length);
    }

    @RabbitListener(queues = COUNT_KEYWORDS)
    public void countKeyWords(TextMessage obj) {
        val pattern = Pattern.compile("[^a-zA-z0-9]?" + obj.keyword() + "[^a-zA-z0-9]");
        val count = pattern.matcher(obj.text()).results().count();
        log.info("Keywords count = " + count);
    }
}
