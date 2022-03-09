package com.bench.project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.bench.project.service.domain.OperationConstant.COUNT_KEYWORDS;
import static com.bench.project.service.domain.OperationConstant.COUNT_WORDS;

@Slf4j
@Configuration
public class RabbitConfiguration {

    public static final String EXCHANGE = "exchange";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Queue countWordsQueue() {
        return new Queue(COUNT_WORDS + "-queue", false);
    }

    @Bean
    Queue countKeyWordsQueue() {
        return new Queue(COUNT_KEYWORDS + "-queue", false);
    }

    @Bean
    Binding countWordsBinding() {
        return BindingBuilder.bind(countWordsQueue()).to(exchange()).with(COUNT_WORDS);
    }

    @Bean
    Binding countKeywordsBinding() {
        return BindingBuilder.bind(countKeyWordsQueue()).to(exchange()).with(COUNT_KEYWORDS);
    }
}
