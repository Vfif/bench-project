package com.bench.project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfiguration {

    public static final String COUNT_WORDS_QUEUE = "cont-words-queue";
    public static final String COUNT_KEY_WORDS_QUEUE = "cont-key-words-queue";
    public static final String EXCHANGE = "exchange";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
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
        return BindingBuilder.bind(countWordsQueue()).to(exchange()).with("#count-words#");
    }

    @Bean
    Binding countKeyWordsBinding() {
        return BindingBuilder.bind(countKeyWordsQueue()).to(exchange()).with("#count-key-word#");
    }
}
