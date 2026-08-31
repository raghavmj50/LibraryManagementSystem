package com.avaali.library.repository;

import com.avaali.library.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer>, JpaSpecificationExecutor<Loan> {

    boolean existsByBookIdAndReturnDateIsNull(Integer bookId);

    long countByMemberIdAndReturnDateIsNull(Integer memberId);


    boolean existsByMemberIdAndReturnDateIsNull(Integer memberId);
}
