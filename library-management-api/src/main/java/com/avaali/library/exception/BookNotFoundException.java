package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends LibraryException{
    public BookNotFoundException(String message){
        super(message, "BOOK_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

}
