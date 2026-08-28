package com.avaali.library.mapper;

import com.avaali.library.dto.request.AuthorRequest;
import com.avaali.library.dto.response.AuthorResponse;
import com.avaali.library.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public static Author create(AuthorRequest request) {

        Author author = new Author();

        author.setName(request.getName());
        author.setNationality(request.getNationality());


        return author;
    }

    public static AuthorResponse doResponse(Author savedauthor, Long bookcount) {

        AuthorResponse response = new AuthorResponse();
        response.setId(savedauthor.getId());
        response.setName(savedauthor.getName());
        response.setNationality(savedauthor.getNationality());
        response.setBookCount(bookcount);

        return response;
    }
}
