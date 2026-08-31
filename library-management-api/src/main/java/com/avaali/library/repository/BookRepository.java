package com.avaali.library.repository;

import com.avaali.library.entity.Book;
import com.avaali.library.specification.BookSpecification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface  BookRepository extends JpaRepository<Book,Integer> , JpaSpecificationExecutor<Book> {

    long countByCategoryId(Integer categoryId);

    long countByAuthorsId(Integer authorId);

    boolean existsByCategoryId(Integer id);

    boolean existsByIsbn(@NotBlank(message = "ISBN cannot be blank") @Pattern(
            regexp = "\\d{13}",
            message = "ISBN must contain exactly 13 digits"
    ) String isbn);

    Page<Book> findAll(Specification<Book> specification, Pageable pageable);
}
