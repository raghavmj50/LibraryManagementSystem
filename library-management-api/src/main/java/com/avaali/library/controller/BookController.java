package com.avaali.library.controller;

import com.avaali.library.dto.request.AuthorRequest;
import com.avaali.library.dto.request.BookRequest;
import com.avaali.library.dto.response.BookResponse;
import com.avaali.library.service.AuthorService;
import com.avaali.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request){

         BookResponse response  = bookService.createBook(request);
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(
            @PathVariable Integer id) {

        BookResponse response = bookService.getBookById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Boolean availableOnly,
            @RequestParam(required = false) Integer publishedAfter,
            Pageable pageable) {

        Page<BookResponse> response =
                bookService.getBooks(
                        title,
                        authorName,
                        categoryId,
                        availableOnly,
                        publishedAfter,
                        pageable
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Integer id,
            @RequestBody @Valid BookRequest request) {

        BookResponse response =
                bookService.updateBook(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable Integer id) {

        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}
