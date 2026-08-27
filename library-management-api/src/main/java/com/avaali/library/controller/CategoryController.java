package com.avaali.library.controller;

import com.avaali.library.dto.request.CategoryRequest;
import com.avaali.library.dto.respond.CategoryResponse;
import com.avaali.library.enity.Category;
import com.avaali.library.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request){

        CategoryResponse response = categoryService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategory(Pageable pageable){

        return ResponseEntity.ok(categoryService.getCategories(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id){

        CategoryResponse response = categoryService.getCategoryById(id);

        return  ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer id , @RequestBody @Valid CategoryRequest request){

        CategoryResponse response = categoryService.updateCategory(id,request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id){

        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
