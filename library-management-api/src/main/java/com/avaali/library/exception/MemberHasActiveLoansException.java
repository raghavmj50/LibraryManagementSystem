package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class MemberHasActiveLoansException extends LibraryException{

    public MemberHasActiveLoansException(String message){

        super(message, "MEMBER_HAS_ACTIVE_LOANS", HttpStatus.CONFLICT);
    }
}
