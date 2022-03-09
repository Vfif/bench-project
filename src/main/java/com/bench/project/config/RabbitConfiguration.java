package com.bench.project.config;

import com.bench.project.service.domain.OperationConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.bench.project.config.RabbitConfiguration.exchange;

@Configuration
public class RabbitConfiguration {

    public static final String EXCHANGE = "exchange";

    @Bean
    static TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    static MyConfigBean configBean() {
        return new MyConfigBean();
    }
}

@Slf4j
class MyConfigBean implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        OperationConstant.list.forEach(it -> {

                String queueName = it + "Queue";
                String bindingName = it + "Binding";

                GenericBeanDefinition qbd = new GenericBeanDefinition();
                qbd.setBeanClass(Queue.class);
                Queue queue = new Queue(it, false);
                qbd.setInstanceSupplier(() -> queue);
                registry.registerBeanDefinition(queueName, qbd);

                GenericBeanDefinition bbq = new GenericBeanDefinition();
                bbq.setBeanClass(Binding.class);
                bbq.setInstanceSupplier(() -> BindingBuilder.bind(queue).to(exchange()).with(it));
                registry.registerBeanDefinition(bindingName, bbq);
            }
        );
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
        throws BeansException {
    }
}
