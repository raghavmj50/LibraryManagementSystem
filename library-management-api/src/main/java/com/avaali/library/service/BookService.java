package com.avaali.library.service;

import com.avaali.library.dto.request.BookRequest;
import com.avaali.library.dto.response.BookResponse;
import com.avaali.library.entity.Author;
import com.avaali.library.entity.Book;
import com.avaali.library.entity.Category;
import com.avaali.library.exception.*;
import com.avaali.library.mapper.BookMapper;
import com.avaali.library.repository.AuthorRepository;
import com.avaali.library.repository.BookRepository;
import com.avaali.library.repository.CategoryRepository;
import com.avaali.library.repository.LoanRepository;
import com.avaali.library.specification.BookSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final LoanRepository loanRepository;
    private List<Author> authors = new ArrayList<>();

    public BookResponse createBook(BookRequest request) {

        // 1. Check whether ISBN already exists
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException(
                    "Book with this ISBN already exists"
            );
        }

        // 2. Find category
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found"
                ));

        // 3. Find authors
        List<Author> authors =
                authorRepository.findAllById(request.getAuthorIds());

        // 4. Make sure all requested authors exist
        if (authors.size() != request.getAuthorIds().size()) {
            throw new AuthorNotFoundException(
                    "One or more authors not found"
            );
        }

        // 5. Create Book entity
        Book book = bookMapper.create(request, category, authors);

        // 6. Save Book
        Book savedBook = bookRepository.save(book);

        // 7. Convert entity to response
        return bookMapper.doResponse(savedBook);
    }

    @Cacheable(value="books" , key="#id")
    public BookResponse getBookById(Integer id) {

        Book book = bookRepository.findBookWithDetailsById(id)
                .orElseThrow(() -> new BookNotFoundException(
                        "Book not found"
                ));

        return bookMapper.doResponse(book);
    }

    @Cacheable(
            value = "books",
            key = "'title=' + #title + ':authorName=' + #authorName + ':categoryId=' + #categoryId + ':availableOnly=' + #availableOnly + ':publishedAfter=' + #publishedAfter + ':size=' + #pageable.pageSize + ':page=' + #pageable.pageNumber + ':sort=' + #pageable.sort"
    )

    public Page<BookResponse> getBooks(
            String title,
            String authorName,
            Integer categoryId,
            Boolean availableOnly,
            Integer publishedAfter,
            Pageable pageable) {

        Specification<Book> specification =
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction();

        if (title != null && !title.isBlank()) {
            specification = specification.and(
                    BookSpecification.titleContains(title)
            );
        }

        if (authorName != null && !authorName.isBlank()) {
            specification = specification.and(
                    BookSpecification.authorNameContains(authorName)
            );
        }

        if (categoryId != null) {
            specification = specification.and(
                    BookSpecification.hasCategoryId(categoryId)
            );
        }

        if (Boolean.TRUE.equals(availableOnly)) {
            specification = specification.and(
                    BookSpecification.isAvailable()
            );
        }

        if (publishedAfter != null) {
            specification = specification.and(
                    BookSpecification.publishedAfter(publishedAfter)
            );
        }

        Page<Book> books =
                bookRepository.findAll(
                        specification,
                        pageable
                );

        return books.map(bookMapper::doResponse);
    }

        public BookResponse updateBook(
                Integer id,
                BookRequest request) {

            // 1. Find existing book
            Book book = bookRepository.findById(id)
                    .orElseThrow(() ->
                            new BookNotFoundException(
                                    "Book not found"
                            ));

            // 2. Check ISBN uniqueness
            if (!book.getIsbn().equals(request.getIsbn())
                    && bookRepository.existsByIsbn(request.getIsbn())) {

                throw new DuplicateResourceException(
                        "ISBN already exists"
                );
            }

            // 3. Find category
            Category category = categoryRepository.findById(
                            request.getCategoryId()
                    )
                    .orElseThrow(() ->
                            new CategoryNotFoundException(
                                    "Category not found"
                            ));

            // 4. Find all authors
            List<Author> authors =
                    request.getAuthorIds()
                            .stream()
                            .map(authorId ->
                                    authorRepository.findById(authorId)
                                            .orElseThrow(() ->
                                                    new AuthorNotFoundException(
                                                            "Author not found"
                                                    ))
                            )
                            .collect(Collectors.toList());
            // 5. Calculate copies currently on loan
            int currentBorrowedCopies =
                    book.getTotalCopies()
                            - book.getAvailableCopies();

            // 6. Validate total copies
            if (request.getTotalCopies()
                    < currentBorrowedCopies) {

                throw new LoanNotFoundException(
                        "Total copies cannot be less than copies currently on loan"
                );
            }

            // 7. Update simple fields
            bookMapper.updateEntity(book, request);

            // 8. Update category
            book.setCategory(category);

            // 9. Update authors
            book.setAuthors(authors);

            // 10. Recalculate available copies
            book.setAvailableCopies(
                    request.getTotalCopies()
                            - currentBorrowedCopies
            );

            // 11. Save existing book
            Book updatedBook =
                    bookRepository.save(book);

            // 12. Convert to response
            return bookMapper.doResponse(updatedBook);
        }


    public void deleteBook(Integer id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException("Book not found"));

        boolean hasActiveLoans =
                loanRepository.existsByBookIdAndReturnDateIsNull(id);

        if (hasActiveLoans) {
            throw new BookHasActiveLoansException(
                    "Book has active loans");
        }

        bookRepository.delete(book);
    }
    }
