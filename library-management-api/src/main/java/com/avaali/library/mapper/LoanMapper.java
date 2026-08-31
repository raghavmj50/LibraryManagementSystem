package com.avaali.library.mapper;

import com.avaali.library.dto.request.LoanRequest;
import com.avaali.library.dto.response.BookSummary;
import com.avaali.library.dto.response.LoanResponse;
import com.avaali.library.dto.response.MemberSummary;
import com.avaali.library.entity.Loan;
import com.avaali.library.entity.LoanStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class LoanMapper {

    public static Loan create(
            LoanRequest request,
            com.avaali.library.entity.Member member,
            com.avaali.library.entity.Book book) {

        Loan loan = new Loan();

        loan.setMember(member);
        loan.setBook(book);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setReturnDate(null);
        loan.setFineAmount(0.0);

        return loan;
    }

    public static LoanResponse doResponse(Loan loan) {

        LoanResponse response = new LoanResponse();

        response.setId(loan.getId());

        MemberSummary memberSummary = new MemberSummary();
        memberSummary.setId(loan.getMember().getId());
        memberSummary.setName(loan.getMember().getName());

        response.setMember(memberSummary);

        BookSummary bookSummary = new BookSummary();
        bookSummary.setId(loan.getBook().getId());
        bookSummary.setTitle(loan.getBook().getTitle());

        response.setBook(bookSummary);

        response.setIssueDate(loan.getIssueDate());
        response.setDueDate(loan.getDueDate());
        response.setReturnDate(loan.getReturnDate());
        response.setFineAmount(BigDecimal.valueOf(loan.getFineAmount()));

        if (loan.getReturnDate() != null) {

            response.setStatus(LoanStatus.RETURNED);

        } else if (LocalDate.now().isAfter(loan.getDueDate())) {

            response.setStatus(LoanStatus.OVERDUE);

        } else {

            response.setStatus(LoanStatus.ACTIVE);
        }

        return response;
    }
}