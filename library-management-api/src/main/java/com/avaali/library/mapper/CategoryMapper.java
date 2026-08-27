package com.avaali.library.mapper;

import com.avaali.library.dto.request.CategoryRequest;
import com.avaali.library.dto.respond.CategoryResponse;
import com.avaali.library.enity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {


    public Category toEntity(CategoryRequest request){

        Category category = new Category();
        category.setName(request.getName());

        return category;
    }

    public static CategoryResponse toResponse(Category category, long bookcount) {

        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setBookCount(bookcount);

        return response;


    }
}
