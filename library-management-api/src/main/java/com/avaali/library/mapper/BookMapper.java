package com.avaali.library.mapper;

import com.avaali.library.dto.request.BookRequest;
import com.avaali.library.dto.response.AuthorResponse;
import com.avaali.library.dto.response.BookResponse;
import com.avaali.library.dto.response.CategoryResponse;
import com.avaali.library.entity.Author;
import com.avaali.library.entity.Book;
import com.avaali.library.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {

    public Book create(
            BookRequest request,
            Category category,
            List<Author> authors) {

        Book book = new Book();

        book.setIsbn(request.getIsbn());
        book.setTitle(request.getTitle());
        book.setPublishedYear(request.getPublishedYear());
        book.setTotalCopies(request.getTotalCopies());

        // Initially all copies are available
        book.setAvailableCopies(request.getTotalCopies());

        book.setCategory(category);
        book.setAuthors(authors);

        return book;
    }

    public BookResponse doResponse(Book book) {

        BookResponse response = new BookResponse();

        response.setId(book.getId());
        response.setIsbn(book.getIsbn());
        response.setTitle(book.getTitle());
        response.setPublishedYear(book.getPublishedYear());
        response.setTotalCopies(book.getTotalCopies());
        response.setAvailableCopies(book.getAvailableCopies());

        // Category mapping
        Category category = book.getCategory();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());

        response.setCategory(categoryResponse);

        // Authors mapping
        List<AuthorResponse> authorResponses = book.getAuthors()
                .stream()
                .map(author -> {

                    AuthorResponse authorResponse = new AuthorResponse();

                    authorResponse.setId(author.getId());
                    authorResponse.setName(author.getName());

                    return authorResponse;
                })
                .toList();

        response.setAuthors(authorResponses);

        return response;
    }

    public void updateEntity(Book book, BookRequest request) {

        book.setIsbn(request.getIsbn());
        book.setTitle(request.getTitle());
        book.setPublishedYear(request.getPublishedYear());
        book.setTotalCopies(request.getTotalCopies());
    }
}