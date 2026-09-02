package com.avaali.library.messaging;

import com.avaali.library.configuration.RabbitMQConfig;
import com.avaali.library.dto.event.LoanCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishLoanCreated(LoanCreatedEvent event) {


        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}