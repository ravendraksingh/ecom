//package com.rks.paymentservice.rabbitmq.config;
//
////import com.rks.paymentservice.rabbitmq.PaymentServiceListener;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class PaymentServiceAMQPConfig {
//
//    @Autowired
//    private PaymentServiceRabbitMQProperties properties;
//
//    @Bean(name = "paymentServiceQueue")
//    @Qualifier("paymentServiceQueue")
//    Queue queue() {
//        return new Queue(properties.getPaymentserviceQueueName(), false);
//    }
//
//    @Bean(name = "paymentServiceExchange")
//    @Qualifier("paymentServiceExchange")
//    TopicExchange exchange() {
//        return new TopicExchange(properties.getPaymentserviceExchangeName());
//    }
//
//    @Bean(name = "paymentServiceBinding")
//    @Qualifier("paymentServiceBinding")
//    Binding binding(@Qualifier("paymentServiceQueue") Queue queue,
//                    @Qualifier("paymentServiceExchange") TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(properties.getPaymentserviceRoutingKey());
//    }
//
////    @Bean(name = "paymentServiceContainer")
////    @Qualifier("paymentServiceContainer")
////    SimpleMessageListenerContainer container
////            (ConnectionFactory connectionFactory1,
////             @Qualifier("paymentServiceListenerAdapter") MessageListenerAdapter listenerAdapter) {
////        SimpleMessageListenerContainer orderCreatedContainer = new SimpleMessageListenerContainer();
////        orderCreatedContainer.setConnectionFactory(connectionFactory1);
////        orderCreatedContainer.setQueueNames(properties.getPaymentserviceQueueName());
////        orderCreatedContainer.setMessageListener(listenerAdapter);
////        return orderCreatedContainer;
////    }
//
////    @Bean
////    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
////        return new MappingJackson2MessageConverter();
////    }
//
////    @Bean(name = "orderServiceAMQPTemplate")
////    @Qualifier("orderServiceAMQPTemplate")
////    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory1) {
////        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory1);
////        rabbitTemplate.setMessageConverter(messageConverter());
////        return rabbitTemplate;
////    }
//
////    @Bean(name = "messageConverter")
////    @Qualifier("messageConverter")
////    public Jackson2JsonMessageConverter messageConverter() {
////        return new Jackson2JsonMessageConverter();
////    }
//
////    @Bean(name = "paymentServiceListenerAdapter")
////    @Qualifier("paymentServiceListenerAdapter")
////    MessageListenerAdapter listenerAdapter(OrderServiceListener listener) {
////        return new MessageListenerAdapter(listener, "listenToOrderCreated");
////    }
//}
