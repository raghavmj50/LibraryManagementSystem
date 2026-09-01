package com.avaali.library.service;

import com.avaali.library.dto.request.LoanRequest;
import com.avaali.library.dto.response.LoanResponse;
import com.avaali.library.entity.Book;
import com.avaali.library.entity.Loan;
import com.avaali.library.entity.Member;
import com.avaali.library.exception.*;
import com.avaali.library.mapper.LoanMapper;
import com.avaali.library.repository.BookRepository;
import com.avaali.library.repository.LoanRepository;
import com.avaali.library.repository.MemberRepository;
import com.avaali.library.specification.LoanSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final Clock clock;


    @Transactional
    public LoanResponse createLoan(LoanRequest request) {

        // 1. Find member
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() ->
                        new MemberNotFoundException(
                                "Member not found"
                        ));

        // 2. Find book
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found"
                        ));

        // 3. Check book availability
        if (book.getAvailableCopies() <= 0) {

            throw new BookNotAvailableException(
                    "Book is not available"
            );
        }

        // 4. Check member's active loans
        long activeLoanCount =
                loanRepository.countByMemberIdAndReturnDateIsNull(
                        member.getId()
                );

        if (activeLoanCount >= 5) {

            throw new LoanLimitExceededException(
                    "Member has reached the loan limit"
            );
        }

        // 5. Create loan
        Loan loan = LoanMapper.create(
                request,
                member,
                book
        );

        // 6. Decrease available copies
        book.setAvailableCopies(
                book.getAvailableCopies() - 1
        );

        bookRepository.save(book);

        // 7. Save loan
        Loan savedLoan = loanRepository.save(loan);

        // 8. Return response
        return LoanMapper.doResponse(savedLoan);
    }


    @Transactional
    public LoanResponse returnLoan(Integer id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found"
                        ));

        if (loan.getReturnDate() != null) {
            throw new LoanAlreadyReturnedException(
                    "Loan has already been returned"
            );
        }

        LocalDate returnDate = LocalDate.now(clock);

        loan.setReturnDate(returnDate);

        if (returnDate.isAfter(loan.getDueDate())) {

            long daysLate =
                    ChronoUnit.DAYS.between(
                            loan.getDueDate(),
                            returnDate
                    );

            double fineAmount = daysLate * 5.0;

            loan.setFineAmount(fineAmount);

        } else {

            loan.setFineAmount(0.0);
        }

        Book book = loan.getBook();

        book.setAvailableCopies(
                book.getAvailableCopies() + 1
        );

        bookRepository.save(book);

        Loan savedLoan = loanRepository.save(loan);

        return LoanMapper.doResponse(savedLoan);
    }


    public LoanResponse getLoanById(Integer id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan not found"
                        ));

        return LoanMapper.doResponse(loan);
    }

    public Page<LoanResponse> getLoans(
            Integer memberId,
            Integer bookId,
            String status,
            Pageable pageable) {

        Specification<Loan> specification =
                LoanSpecification.filter(
                        memberId,
                        bookId,
                        status
                );

        Page<Loan> loans =
                loanRepository.findAll(
                        specification,
                        pageable
                );

        return loans.map(LoanMapper::doResponse);
    }
}