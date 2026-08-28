package com.avaali.library.controller;

import com.avaali.library.dto.request.AuthorRequest;
import com.avaali.library.dto.response.AuthorResponse;
import com.avaali.library.entity.Author;
import com.avaali.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor

public class AuthorController {

    private final AuthorService authorService;
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody @Valid AuthorRequest request){

         AuthorResponse response  = authorService.createAuthor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AuthorResponse>> getAuthors(@RequestParam (required = false)String name, Pageable pageable){

        Page<AuthorResponse> resposne = authorService.getAuthors(name,pageable);

        return ResponseEntity.ok(resposne);

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Integer id){

          AuthorResponse resposne  = authorService.getAuthorById(id);

          return ResponseEntity.ok(resposne);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor( @PathVariable Integer id ,@RequestBody @Valid AuthorRequest request){

        AuthorResponse resposne  = authorService.updateAuthor(id,request);

        return ResponseEntity.ok(resposne);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthor(@PathVariable Integer id){

          authorService.deleteAuthor(id);

         return ResponseEntity.noContent().build();

    }

}
