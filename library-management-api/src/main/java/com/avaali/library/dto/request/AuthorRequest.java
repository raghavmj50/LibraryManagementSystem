package com.avaali.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(
            min = 2,
            max = 100,
            message = "Name must be between 2 and 100 characters"
    )
    private String name;

    @Size(
            max = 60,
            message = "Nationality cannot exceed 60 characters"
    )
    private String nationality;
}