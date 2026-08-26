package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class BookHasActiveLoansException extends LibraryException{

    public BookHasActiveLoansException(String message){
        super(message, "BOOK_HAS_ACTIVE_LOANS", HttpStatus.CONFLICT);

    }
}
