package com.avaali.library.messaging;

import com.avaali.library.configuration.RabbitMQConfig;
import com.avaali.library.dto.event.LoanCreatedEvent;
import com.avaali.library.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanEventConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumeLoanCreated(LoanCreatedEvent event) {

        System.out.println(
                "Received LOAN_CREATED event: " +
                        "loanId=" + event.getLoanId() +
                        ", memberId=" + event.getMemberId() +
                        ", bookId=" + event.getBookId()
        );

        emailService.sendLoanCreatedEmail(
                event.getMemberEmail(),
                event.getMemberName(),
                event.getBookTitle(),
                event.getDueDate().toString()
        );
    }
}