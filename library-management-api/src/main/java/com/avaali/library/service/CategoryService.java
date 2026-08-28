package com.avaali.library.service;

import com.avaali.library.dto.request.CategoryRequest;
import com.avaali.library.dto.response.CategoryResponse;
import com.avaali.library.entity.Category;
import com.avaali.library.exception.LibraryException;
import com.avaali.library.mapper.CategoryMapper;
import com.avaali.library.repository.BookRepository;
import com.avaali.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new LibraryException(
                    "Category already exists",
                    "DUPLICATE_RESOURCE",
                    HttpStatus.CONFLICT
            );
        }

        //So here convert the request dto to entity here
         Category category = categoryMapper.toEntity(request);

        //soo here we have to convert the entity in to the mapper resposne again
        //AND AGAIN FROM THERE TO THE RESPOSNEDTO HERE

         Category savedCategory = categoryRepository.save(category);

         long bookcount = bookRepository.countByCategoryId(savedCategory.getId());

          return  categoryMapper.toResponse(savedCategory,bookcount);



    }

    public Page<CategoryResponse> getCategories(Pageable pageable) {

        Page<Category> categories = categoryRepository.findAll(pageable);

        return categories.map(category->{
            long bookcount = bookRepository.countByCategoryId(category.getId());

            return categoryMapper.toResponse(category,bookcount);
        });

    }

    public CategoryResponse getCategoryById(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new LibraryException(
                        "Category not found with id: " + id,
                        "CATEGORY_NOT_FOUND",
                        HttpStatus.NOT_FOUND
                ));
        long bookcount = bookRepository.countByCategoryId(category.getId());
        return CategoryMapper.toResponse(category,bookcount);

    }

    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {

        Category category1 = categoryRepository.findById(id)
                .orElseThrow(() -> new LibraryException(
                        "Category not found with id: " + id,
                        "CATEGORY_NOT_FOUND",
                        HttpStatus.NOT_FOUND
                ));

        category1.setName(request.getName());

        Category category2 = categoryRepository.save(category1);

        long bookcount = bookRepository.countByCategoryId(category2.getId());

        return categoryMapper.toResponse(category2,bookcount);
    }

    public void deleteCategory(Integer id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new LibraryException(
                        "Category not found with id: " + id,
                        "CATEGORY_NOT_FOUND",
                        HttpStatus.NOT_FOUND
                ));

        if (bookRepository.existsByCategoryId(id)) {
            throw new LibraryException(
                    "Category cannot be deleted because it has books",
                    "CATEGORY_IN_USE",
                    HttpStatus.CONFLICT
            );
        }

        categoryRepository.delete(category);

    }
}
