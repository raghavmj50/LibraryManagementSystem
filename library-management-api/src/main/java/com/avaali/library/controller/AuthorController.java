package com.avaali.library.controller;

import com.avaali.library.dto.request.AuthorRequest;
import com.avaali.library.dto.respond.AuthorResponse;
import com.avaali.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private AuthorService authorService;
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody @Valid AuthorRequest request){

         AuthorResponse response  = authorService.createAuthor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
