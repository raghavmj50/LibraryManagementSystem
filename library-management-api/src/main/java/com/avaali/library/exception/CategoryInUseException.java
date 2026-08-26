package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class CategoryInUseException extends LibraryException{

    public CategoryInUseException(String message){
        super(message, "CATEGORY_IN_USE", HttpStatus.CONFLICT);
    }
}
