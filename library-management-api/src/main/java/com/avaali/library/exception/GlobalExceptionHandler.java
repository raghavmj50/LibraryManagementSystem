package com.avaali.library.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles your custom LibraryExceptions
    @ExceptionHandler(LibraryException.class)
    public ProblemDetail handleLibraryException(
            LibraryException ex,
            HttpServletRequest request) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(ex.getStatus());

        problemDetail.setTitle(ex.getStatus().getReasonPhrase());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("errorCode", ex.getErrorCode());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty(
                "instance",
                request.getRequestURI()
        );

        return problemDetail;
    }

    // Handles @Valid validation failures
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Bad Request");

        problemDetail.setDetail(
                "Validation failed for "
                        + ex.getBindingResult().getFieldErrorCount()
                        + " field(s)"
        );

        problemDetail.setProperty(
                "errorCode",
                "VALIDATION_FAILED"
        );

        problemDetail.setProperty(
                "timestamp",
                Instant.now()
        );

        problemDetail.setProperty(
                "instance",
                request.getRequestURI()
        );

        List<Object> errors = new ArrayList<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    errors.add(
                            Map.of(
                                    "field",
                                    error.getField(),
                                    "message",
                                    error.getDefaultMessage()
                            )
                    );
                });

        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    // Scenario 32:
    // Invalid JSON such as { "title": }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleMalformedRequest(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail("Request body is not valid JSON");
        problemDetail.setProperty(
                "errorCode",
                "MALFORMED_REQUEST"
        );
        problemDetail.setProperty(
                "timestamp",
                Instant.now()
        );
        problemDetail.setProperty(
                "instance",
                request.getRequestURI()
        );

        return problemDetail;
    }

    // Scenario 33:
    // GET /api/books/abc when id should be Long
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Bad Request");

        problemDetail.setDetail(
                "Invalid value for parameter: "
                        + ex.getName()
        );

        problemDetail.setProperty(
                "errorCode",
                "MALFORMED_REQUEST"
        );

        problemDetail.setProperty(
                "timestamp",
                Instant.now()
        );

        problemDetail.setProperty(
                "instance",
                request.getRequestURI()
        );

        return problemDetail;
    }

    // Handles anything unexpected
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(
            Exception ex,
            HttpServletRequest request) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR
                );

        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail("An unexpected error occurred");
        problemDetail.setProperty(
                "errorCode",
                "INTERNAL_ERROR"
        );
        problemDetail.setProperty(
                "timestamp",
                Instant.now()
        );
        problemDetail.setProperty(
                "instance",
                request.getRequestURI()
        );

        return problemDetail;
    }
}