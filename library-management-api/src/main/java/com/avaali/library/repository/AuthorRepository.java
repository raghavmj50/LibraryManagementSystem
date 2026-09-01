package com.avaali.library.repository;

import com.avaali.library.entity.Author;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Page<Author> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

}
