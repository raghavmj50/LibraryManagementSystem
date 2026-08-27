package com.avaali.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequest {

    @NotBlank(message="it cannot be blank")
    @Size(min = 2 , max = 100 , message ="It should be between 2 to 100 characters")
    private String name;

    @Size(max = 60, message ="maximum characters are to be 60")
    private String nationality;

}
