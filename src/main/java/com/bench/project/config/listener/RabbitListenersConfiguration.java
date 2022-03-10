package com.bench.project.config.listener;

import com.bench.project.service.domain.TextMessage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.regex.Pattern;

import static com.bench.project.config.OperationConstants.COUNT_KEYWORDS;
import static com.bench.project.config.OperationConstants.COUNT_WORDS;
import static com.bench.project.config.OperationConstants.RANDOM_LIST;

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
        String keyword = obj.extraInfo().get("keyword");
        val pattern = Pattern.compile("[^a-zA-z0-9]?" + keyword + "[^a-zA-z0-9]");
        val count = pattern.matcher(obj.text()).results().count();
        log.info("Keywords count = " + count);
    }

    @RabbitListener(queues = RANDOM_LIST)
    public void random(TextMessage obj) {
        RandomGenerator generator = RandomGeneratorFactory.of("Xoshiro256PlusPlus").create(999);
        List<String> list = Arrays.asList(obj.text().split(" "));
        Collections.shuffle(list, (Random) generator);

        log.info("Randomize list: " + list);
    }
}
