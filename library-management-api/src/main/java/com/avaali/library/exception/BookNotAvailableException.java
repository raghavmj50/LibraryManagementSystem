package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class BookNotAvailableException extends LibraryException{
    public BookNotAvailableException(String message){
        super(
                message,
                "BOOK_NOT_FOUND",
                HttpStatus.NOT_FOUND
        );
    }
}
