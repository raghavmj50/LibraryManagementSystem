package com.avaali.library.messaging;

import com.avaali.library.dto.event.LoanCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LoanKafkaConsumer {

    @KafkaListener(
            topics = "loan-created",
            groupId = "library-consumer-group"
    )
    public void consumeLoanCreated(LoanCreatedEvent event) {

        System.out.println(
                "Received LOAN_CREATED event from Kafka: " +
                        "loanId=" + event.getLoanId() +
                        ", memberId=" + event.getMemberId() +
                        ", bookId=" + event.getBookId()
        );
    }
}