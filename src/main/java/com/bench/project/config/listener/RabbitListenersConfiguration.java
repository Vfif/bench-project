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

import static com.bench.project.config.OperationConstants.*;

@Slf4j
@Component
@EnableRabbit
@RequiredArgsConstructor
public class RabbitListenersConfiguration {

    private final LogDao dao;

    private static final RandomGenerator generator = RandomGeneratorFactory.of("Xoshiro256PlusPlus").create(999);
    private static final Random random = new Random() {
        @Override
        public int nextInt(int bound) { // only this method is used in Collections.shuffle
            return generator.nextInt(bound);
        }
    };

    @RabbitListener(queues = COUNT_WORDS)
    public void countWords(TextMessage obj) {

        val wordsList = obj.text().split(" ");

        int count = wordsList.length;
        log.info("Words count = " + count);
        dao.save(LogDto.from(COUNT_WORDS, null, obj, String.valueOf(count)));
    }

    @RabbitListener(queues = COUNT_KEYWORDS)
    public void countKeyWords(TextMessage obj) {

        val extraInfo = obj.extraInfo();
        if (extraInfo == null) {

            log.warn("No extra info specified in request");
            return;
        }

        String keyword = extraInfo.get("keyword");

        if (keyword == null) {

            log.warn("No keyword specified in request");
            return;
        }

        val pattern = Pattern.compile("[^a-zA-z0-9]?" + keyword + "[^a-zA-z0-9]");
        val count = pattern.matcher(obj.text()).results().count();

        log.info("Keyword '" + keyword + "' count = " + count);
        dao.save(LogDto.from(COUNT_KEYWORDS, keyword, obj, String.valueOf(count)));
    }

    @RabbitListener(queues = RANDOM)
    public void random(TextMessage obj) {

        List<String> list = Arrays.asList(obj.text().split(" "));
        Collections.shuffle(list, random);

        log.info("Randomized list: " + list);
        dao.save(LogDto.from(COUNT_KEYWORDS, null, obj, list.toString()));
    }
}
