package com.avaali.library.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookResponse {

    private Integer id;
    private String isbn;
    private String title;
    private Integer publishedYear;
    private Integer totalCopies;
    private Integer availableCopies;

    private CategoryResponse category;
    private List<AuthorResponse> authors;
}