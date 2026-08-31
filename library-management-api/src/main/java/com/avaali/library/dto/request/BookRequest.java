package com.avaali.library.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookRequest {

    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(
            regexp = "\\d{13}",
            message = "ISBN must contain exactly 13 digits"
    )
    private String isbn;

    @NotBlank(message = "Title cannot be blank")
    @Size(
            max = 200,
            message = "Title cannot exceed 200 characters"
    )
    private String title;

    @Min(
            value = 1450,
            message = "Published year must be at least 1450"
    )
    private Integer publishedYear;

    @Min(
            value = 1,
            message = "Total copies must be at least 1"
    )
    @Max(
            value = 1000,
            message = "Total copies cannot exceed 1000"
    )
    private Integer totalCopies;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotEmpty(message = "At least one author ID is required")
    private List<Integer> authorIds;
}