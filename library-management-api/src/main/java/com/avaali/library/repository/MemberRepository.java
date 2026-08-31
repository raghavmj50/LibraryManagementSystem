package com.avaali.library.repository;

import com.avaali.library.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {

    boolean existsByEmail(String email);

    Page<Member> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<Member> findByEmailContainingIgnoreCase(
            String email,
            Pageable pageable
    );

    Page<Member> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
            String name,
            String email,
            Pageable pageable
    );

}
