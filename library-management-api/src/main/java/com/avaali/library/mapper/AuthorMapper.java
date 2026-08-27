package com.avaali.library.mapper;

import com.avaali.library.dto.request.AuthorRequest;
import com.avaali.library.dto.respond.AuthorResponse;
import com.avaali.library.enity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public static Author create(AuthorRequest request) {

        Author author = new Author();

        author.setName(request.getName());
        author.setNationality(request.getNationality());


        return author;
    }

    public static AuthorResponse doResponse(Author savedauthor) {


    }
}
