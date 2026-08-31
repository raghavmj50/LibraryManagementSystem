package com.avaali.library.specification;

import com.avaali.library.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> titleContains(String title) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

    public static Specification<Book> authorNameContains(String authorName) {

        return (root, query, criteriaBuilder) -> {

            var authors = root.join("authors");

            return criteriaBuilder.like(
                    criteriaBuilder.lower(authors.get("name")),
                    "%" + authorName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Book> hasCategoryId(Integer categoryId) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("category").get("id"),
                        categoryId
                );
    }

    public static Specification<Book> isAvailable() {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(
                        root.get("availableCopies"),
                        0
                );
    }

    public static Specification<Book> publishedAfter(Integer publishedAfter) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(
                        root.get("publishedYear"),
                        publishedAfter
                );
    }
}