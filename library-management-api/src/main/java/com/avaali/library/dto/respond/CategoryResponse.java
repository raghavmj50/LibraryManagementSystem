package com.avaali.library.dto.respond;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponse {

    private Integer id;

    private String name;

    private Long bookCount;
}
