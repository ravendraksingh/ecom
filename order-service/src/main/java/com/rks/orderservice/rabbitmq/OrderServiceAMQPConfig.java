package com.rks.orderservice.rabbitmq;

import com.rks.orderservice.rabbitmq.OrderServiceRabbitMQProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(
        value = "rabbitmq.enabled",
        havingValue = "true",
        matchIfMissing = false
)
@Configuration
public class OrderServiceAMQPConfig {

    @Autowired
    private OrderServiceRabbitMQProperties properties;

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    Queue queue() {
        return new Queue(properties.getOrderserviceQueueName(), false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(properties.getOrderserviceExchangeName());
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(properties.getOrderserviceRoutingKey());
    }

    @Bean(name = "paymentServiceListenerAdapter")
    @Qualifier(value = "paymentServiceListenerAdapter")
    MessageListenerAdapter paymentServiceListenerAdapter(PaymentMessageListener listener) {
        return new MessageListenerAdapter(listener, "listenToPaymentQueue");
    }

    @Bean
    SimpleMessageListenerContainer paymentServiceListenerContainer(
            @Qualifier("paymentServiceListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer orderCreatedContainer = new SimpleMessageListenerContainer();
        orderCreatedContainer.setConnectionFactory(connectionFactory());
        orderCreatedContainer.setQueueNames("paymentservice-queue");
        orderCreatedContainer.setMessageListener(listenerAdapter);
        return orderCreatedContainer;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
