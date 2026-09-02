package com.avaali.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendLoanCreatedEmail(
            String to,
            String memberName,
            String bookTitle,
            String dueDate) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Book Borrowed Successfully");

        message.setText(
                "Hello " + memberName + ",\n\n" +
                        "You have successfully borrowed the book: " + bookTitle + ".\n\n" +
                        "Due Date: " + dueDate + "\n\n" +
                        "Thank you for using our library."
        );

        mailSender.send(message);
    }
}