package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class AuthorNotFoundException extends LibraryException{

    public AuthorNotFoundException(String message){
        super(message, "AUTHOR_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
