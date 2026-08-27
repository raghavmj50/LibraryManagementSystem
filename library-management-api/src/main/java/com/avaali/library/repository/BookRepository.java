package com.avaali.library.repository;

import com.avaali.library.enity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  BookRepository extends JpaRepository<Book,Integer> {

    long countByCategoryId(Integer categoryId);

    boolean existsByCategoryId(Integer id);
}
