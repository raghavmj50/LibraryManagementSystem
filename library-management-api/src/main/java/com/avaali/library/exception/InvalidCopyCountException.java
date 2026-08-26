package com.avaali.library.exception;

import org.springframework.http.HttpStatus;

public class InvalidCopyCountException extends LibraryException {

    public InvalidCopyCountException(String message){
        super(message, "INVALID_COPY_COUNT", HttpStatus.CONFLICT);

    }
}
