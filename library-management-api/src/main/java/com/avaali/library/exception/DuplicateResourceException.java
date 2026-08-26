package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends LibraryException{

    public DuplicateResourceException(String message){
        super(message, "DUPLICATE_RESOURCE", HttpStatus.CONFLICT);
    }
}
