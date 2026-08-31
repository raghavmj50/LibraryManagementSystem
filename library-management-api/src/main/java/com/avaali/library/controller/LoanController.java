package com.avaali.library.controller;

import com.avaali.library.dto.request.LoanRequest;
import com.avaali.library.dto.response.LoanResponse;
import com.avaali.library.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(
            @Valid @RequestBody LoanRequest request) {

        LoanResponse response =
                loanService.createLoan(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(
            @PathVariable Integer id) {

        LoanResponse response =
                loanService.returnLoan(id);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoanById(
            @PathVariable Integer id) {

        LoanResponse response =
                loanService.getLoanById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<LoanResponse>> getLoans(
            @RequestParam(required = false) Integer memberId,
            @RequestParam(required = false) Integer bookId,
            @RequestParam(required = false) String status,
            Pageable pageable) {

        Page<LoanResponse> response =
                loanService.getLoans(
                        memberId,
                        bookId,
                        status,
                        pageable
                );

        return ResponseEntity.ok(response);
    }
}