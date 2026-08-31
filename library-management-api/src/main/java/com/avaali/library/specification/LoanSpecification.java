package com.avaali.library.specification;

import com.avaali.library.entity.Loan;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanSpecification {

    public static Specification<Loan> filter(
            Integer memberId,
            Integer bookId,
            String status) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (memberId != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("member").get("id"),
                                memberId
                        )
                );
            }

            if (bookId != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("book").get("id"),
                                bookId
                        )
                );
            }

            if (status != null && !status.isBlank()) {

                LocalDate today = LocalDate.now();

                if (status.equalsIgnoreCase("RETURNED")) {

                    predicates.add(
                            criteriaBuilder.isNotNull(
                                    root.get("returnDate")
                            )
                    );

                } else if (status.equalsIgnoreCase("ACTIVE")) {

                    predicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.isNull(
                                            root.get("returnDate")
                                    ),
                                    criteriaBuilder.greaterThanOrEqualTo(
                                            root.get("dueDate"),
                                            today
                                    )
                            )
                    );

                } else if (status.equalsIgnoreCase("OVERDUE")) {

                    predicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.isNull(
                                            root.get("returnDate")
                                    ),
                                    criteriaBuilder.lessThan(
                                            root.get("dueDate"),
                                            today
                                    )
                            )
                    );
                }
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}