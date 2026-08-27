package com.avaali.library.repository;

import com.avaali.library.enity.Category;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByName(String name);

    Page<Category> findAll(Pageable pageable);
}
