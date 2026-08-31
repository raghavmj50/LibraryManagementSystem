package com.avaali.library.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanRequest {

    @NotNull(message = "Member ID is required")
    private Integer memberId;

    @NotNull(message = "Book ID is required")
    private Integer bookId;
}