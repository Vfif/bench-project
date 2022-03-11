package com.bench.project.config.listener;

import com.bench.project.service.dao.LogDao;
import com.bench.project.service.domain.LogDto;
import com.bench.project.service.domain.TextMessage;
import lombok.RequiredArgsConstructor;
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
import static com.bench.project.config.OperationConstants.RANDOM;

@Slf4j
@Component
@EnableRabbit
@RequiredArgsConstructor
public class RabbitListenersConfiguration {

    private final LogDao dao;

    @RabbitListener(queues = COUNT_WORDS)
    public void countWords(TextMessage obj) {
        val wordsList = obj.text().split(" ");

        int count = wordsList.length;
        log.info("Words count = " + count);
        dao.save(LogDto.from(COUNT_WORDS, null, obj, String.valueOf(count)));
    }

    @RabbitListener(queues = COUNT_KEYWORDS)
    public void countKeyWords(TextMessage obj) {
        String keyword = obj.extraInfo().get("keyword");

        val pattern = Pattern.compile("[^a-zA-z0-9]?" + keyword + "[^a-zA-z0-9]");
        val count = pattern.matcher(obj.text()).results().count();

        log.info("Keywords count = " + count);
        dao.save(LogDto.from(COUNT_KEYWORDS, keyword, obj, String.valueOf(count)));
    }

    @RabbitListener(queues = RANDOM)
    public void random(TextMessage obj) {
        RandomGenerator generator = RandomGeneratorFactory.of("Xoshiro256PlusPlus").create(999);
        List<String> list = Arrays.asList(obj.text().split(" "));

        Random random = new Random(){
            @Override
            public int nextInt(int bound) { // only this method is used in  Collections.shuffle
                return generator.nextInt(bound);
            }
        };

        Collections.shuffle(list, random);

        log.info("Randomize list: " + list);
        dao.save(LogDto.from(COUNT_KEYWORDS, null, obj, list.toString()));
    }
}
