package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class MemberHasOverdueLoanException extends LibraryException{

    public MemberHasOverdueLoanException(String message){
        super(message,"MEMBER_HAS_OVERDUE_LOAN", HttpStatus.CONFLICT);
    }
}
