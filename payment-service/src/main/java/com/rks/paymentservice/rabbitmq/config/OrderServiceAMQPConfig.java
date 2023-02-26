package com.rks.paymentservice.rabbitmq.config;

//import com.rks.paymentservice.rabbitmq.PaymentServiceListener;
import com.rks.paymentservice.rabbitmq.OrderServiceListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

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

    @Bean(name = "orderServiceContainer")
    @Qualifier("orderServiceContainer")
    SimpleMessageListenerContainer container
            (ConnectionFactory connectionFactory,
             @Qualifier("orderServiceListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer orderServiceContainer = new SimpleMessageListenerContainer();
        orderServiceContainer.setConnectionFactory(connectionFactory);
        orderServiceContainer.setQueueNames(properties.getOrderserviceQueueName());
        orderServiceContainer.setMessageListener(listenerAdapter);
        return orderServiceContainer;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(messageConverter());
        template.setRoutingKey(properties.getOrderserviceRoutingKey());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "orderServiceListenerAdapter")
    @Qualifier("orderServiceListenerAdapter")
    MessageListenerAdapter listenerAdapter(OrderServiceListener listener) {
        return new MessageListenerAdapter(listener, "listenToOrderCreated");
    }
}
