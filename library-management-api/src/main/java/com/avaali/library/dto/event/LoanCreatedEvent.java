package com.avaali.library.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanCreatedEvent {

    private Integer loanId;
    private Integer memberId;
    private Integer bookId;

    private String memberName;
    private String memberEmail;
    private String bookTitle;
    private LocalDate dueDate;
}