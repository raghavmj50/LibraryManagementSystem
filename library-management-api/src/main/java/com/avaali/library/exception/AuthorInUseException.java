package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class AuthorInUseException extends LibraryException{

    public AuthorInUseException(String message){

        super(message, "AUTHOR_IN_USE", HttpStatus.CONFLICT);
    }
}
