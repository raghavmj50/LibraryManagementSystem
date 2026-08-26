package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class LoanLimitExceededException extends LibraryException{

    public LoanLimitExceededException(String message){
        super(message, "LOAN_LIMIT_EXCEEDED", HttpStatus.CONFLICT);

    }
}
