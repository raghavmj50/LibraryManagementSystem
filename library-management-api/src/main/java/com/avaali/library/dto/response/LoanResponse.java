package com.avaali.library.dto.response;

import com.avaali.library.entity.LoanStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanResponse {

    private Integer id;

    private MemberSummary member;

    private BookSummary book;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private BigDecimal fineAmount;

    private LoanStatus status;
}