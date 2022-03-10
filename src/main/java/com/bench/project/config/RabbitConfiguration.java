package com.bench.project.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String EXCHANGE = "exchange";

    @Bean
    static TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }
}
