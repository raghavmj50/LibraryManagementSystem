package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends LibraryException{

    public CategoryNotFoundException(String message){
        super(message, "CATEGORY_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
