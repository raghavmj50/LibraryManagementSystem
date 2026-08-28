package com.avaali.library.repository;

import com.avaali.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BookRepository extends JpaRepository<Book,Integer> {

    long countByCategoryId(Integer categoryId);

    long countByAuthorsId(Integer authorId);

    boolean existsByCategoryId(Integer id);
}
