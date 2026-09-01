package com.avaali.library.service;

import com.avaali.library.dto.request.AuthorRequest;
import com.avaali.library.dto.response.AuthorResponse;
import com.avaali.library.entity.Author;
import com.avaali.library.exception.AuthorInUseException;
import com.avaali.library.exception.AuthorNotFoundException;
import com.avaali.library.mapper.AuthorMapper;
import com.avaali.library.repository.AuthorRepository;
import com.avaali.library.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorResponse createAuthor(AuthorRequest request) {

        Author author = AuthorMapper.create(request);

        Author savedauthor = authorRepository.save(author);

        Long bookcount = bookRepository.countByCategoryId(savedauthor.getId());

        return AuthorMapper.doResponse(savedauthor, bookcount);

    }

    @Cacheable(
            value = "authors",
            key = "'name=' + #name + ':page=' + #pageable.pageNumber + ':size=' + #pageable.pageSize + ':sort=' + #pageable.sort")
    public Page<AuthorResponse> getAuthors(
            String name,
            Pageable pageable) {

        Page<Author> authors;

        if (name == null || name.isBlank()) {

            authors = authorRepository.findAll(pageable);

        } else {

            authors = authorRepository
                    .findByNameContainingIgnoreCase(
                            name,
                            pageable
                    );
        }

        return authors.map(author -> {

            Long bookCount =
                    bookRepository.countByAuthorsId(
                            author.getId()
                    );

            return AuthorMapper.doResponse(
                    author,
                    bookCount
            );
        });
    }

    @Cacheable(value="Author" , key="#id")
    public AuthorResponse getAuthorById(Integer id) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(
                        "Author not present"
                ));

        Long bookcount = bookRepository.countByAuthorsId(id);

        return AuthorMapper.doResponse(author, bookcount);
    }

    public AuthorResponse updateAuthor(Integer id,  AuthorRequest request) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author not found"));

        author.setName(request.getName());
        author.setNationality(request.getNationality());

        Author updatedAuthor = authorRepository.save(author);

        Long bookCount =
                bookRepository.countByAuthorsId(updatedAuthor.getId());

        return AuthorMapper.doResponse(
                updatedAuthor,
                bookCount);
    }

    public void deleteAuthor(Integer id) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author not found"));

        Long bookCount =
                bookRepository.countByAuthorsId(id);

        if (bookCount > 0) {
            throw new AuthorInUseException(
                    "Author is already in use"
            );
        }

        authorRepository.delete(author);
    }

}
