package com.bench.project.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import static com.bench.project.config.RabbitConfiguration.exchange;

/**
 * dynamically create Queue and Binding beans for each element in list of {@link OperationConstants}
 */
@Component
class QueueAndBindingRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        OperationConstants.list.forEach(operationName -> {
                Queue queue = registryQueue(registry, operationName);
                registryBinding(registry, operationName, queue);
            }
        );
    }

    private void registryBinding(BeanDefinitionRegistry registry, String operationName, Queue queue) {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(Binding.class);
        bd.setInstanceSupplier(() -> BindingBuilder.bind(queue).to(exchange()).with(operationName));

        String bindingBeanName = operationName + "Binding";
        registry.registerBeanDefinition(bindingBeanName, bd);
    }

    private Queue registryQueue(BeanDefinitionRegistry registry, String operationName) {
        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(Queue.class);
        Queue queue = new Queue(operationName, false);
        bd.setInstanceSupplier(() -> queue);

        String queueBeanName = operationName + "Queue";
        registry.registerBeanDefinition(queueBeanName, bd);
        return queue;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
        throws BeansException {
    }
}
