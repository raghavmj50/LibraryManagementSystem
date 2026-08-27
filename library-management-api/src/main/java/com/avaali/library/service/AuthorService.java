package com.avaali.library.service;

import com.avaali.library.dto.request.AuthorRequest;
import com.avaali.library.dto.respond.AuthorResponse;
import com.avaali.library.enity.Author;
import com.avaali.library.mapper.AuthorMapper;
import com.avaali.library.repository.AuthorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorResponse createAuthor(AuthorRequest request) {

         Author author = AuthorMapper.create(request);

          Author savedauthor = authorRepository.save(author);

         return AuthorMapper.doResponse(savedauthor);

    }
}
