package com.avaali.library.messaging;

import com.avaali.library.dto.event.LoanCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanKafkaPublisher {

    private static final String TOPIC = "loan-created";

    private final KafkaTemplate<String, LoanCreatedEvent> kafkaTemplate;

    public void publishLoanCreated(LoanCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}