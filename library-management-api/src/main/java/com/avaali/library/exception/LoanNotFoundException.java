package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class LoanNotFoundException extends LibraryException{

    public LoanNotFoundException(String message){
        super(message, "LOAN_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
