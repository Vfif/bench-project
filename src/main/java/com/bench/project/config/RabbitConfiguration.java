package com.bench.project.config;

import com.bench.project.service.domain.CountKeyWordsRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
@EnableRabbit
@Slf4j
public class RabbitConfiguration {

    public static final String DEFAULT_EXCHANGE = "default-exchange";
    public static final String DEFAULT_QUEUE = "default-queue";
    public static final String DEFAULT_ROUTING_KEY = "hello";

    public static final String COUNT_WORDS_QUEUE = "cont-words-queue";
    public static final String COUNT_KEY_WORDS_QUEUE = "cont-key-words-queue";

    @Bean
    Queue defaultQueue() {
        return new Queue(DEFAULT_QUEUE, false);
    }

    @Bean
    TopicExchange defaultExchange() {
        return new TopicExchange(DEFAULT_EXCHANGE);
    }

    @Bean
    Binding defaultBinding() {
        return BindingBuilder.bind(defaultQueue()).to(defaultExchange()).with(DEFAULT_ROUTING_KEY);
    }

    @RabbitListener(queues = DEFAULT_QUEUE)
    public void processQueue(String message) {
        log.info("New message " + message);
    }

    @Bean
    Queue countWordsQueue() {
        return new Queue(COUNT_WORDS_QUEUE, false);
    }

    @Bean
    Queue countKeyWordsQueue() {
        return new Queue(COUNT_KEY_WORDS_QUEUE, false);
    }

    @Bean
    Binding countWordsBinding() {
        return BindingBuilder.bind(countWordsQueue()).to(defaultExchange()).with("count-words");
    }

    @Bean
    Binding countKeyWordsBinding() {
        return BindingBuilder.bind(countKeyWordsQueue()).to(defaultExchange()).with("count-key-words");
    }

    @RabbitListener(queues = COUNT_WORDS_QUEUE)
    public void countWords(String message) {
        val wordsList = message.split(" ");
        log.info("Words count = " + wordsList.length);
    }

    @RabbitListener(queues = COUNT_KEY_WORDS_QUEUE)
    public void countKeyWords(CountKeyWordsRequest request) {
        val pattern = Pattern.compile("[^a-zA-z0-9]?" + request.keyword() + "[^a-zA-z0-9]");
        val count = pattern.matcher(request.message()).results().count();
        log.info("Keywords count = " + count);
    }
}
