package com.avaali.library.exception;


import org.springframework.http.HttpStatus;

public class LoanAlreadyReturnedException extends LibraryException {

    public LoanAlreadyReturnedException(String message){
        super(message, "LOAN_ALREADY_RETURNED", HttpStatus.CONFLICT);

    }
}
