package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends LibraryException{

    public MemberNotFoundException(String message){
        super(message, "MEMBER_NOT_FOUND", HttpStatus.NOT_FOUND);

    }
}
