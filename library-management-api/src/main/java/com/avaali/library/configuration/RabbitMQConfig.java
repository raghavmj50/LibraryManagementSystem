package com.avaali.library.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "library.exchange";
    public static final String QUEUE = "loan.created.queue";
    public static final String ROUTING_KEY = "loan.created";


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public TopicExchange libraryExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue loanCreatedQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding loanCreatedBinding(
            Queue loanCreatedQueue,
            TopicExchange libraryExchange) {

        return BindingBuilder
                .bind(loanCreatedQueue)
                .to(libraryExchange)
                .with(ROUTING_KEY);
    }
}