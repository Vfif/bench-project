package com.bench.project.config.listener;

import com.bench.project.service.domain.CountKeyWordsRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import static com.bench.project.config.RabbitConfiguration.COUNT_KEY_WORDS_QUEUE;
import static com.bench.project.config.RabbitConfiguration.COUNT_WORDS_QUEUE;

@Slf4j
@Component
@EnableRabbit
public class RabbitListenerConfiguration {

    @RabbitListener(queues = COUNT_WORDS_QUEUE)
    public void countWords(CountKeyWordsRequest obj) {
        val wordsList = obj.text().split(" ");
        log.info("Words count = " + wordsList.length);
    }

    @RabbitListener(queues = COUNT_KEY_WORDS_QUEUE)
    public void countKeyWords(CountKeyWordsRequest obj) {
        val pattern = Pattern.compile("[^a-zA-z0-9]?" + obj.keyWord() + "[^a-zA-z0-9]");
        val count = pattern.matcher(obj.text()).results().count();
        log.info("Keywords count = " + count);
    }
}
