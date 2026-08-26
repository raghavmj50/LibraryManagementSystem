CREATE TABLE loan (
                      id SERIAL PRIMARY KEY,

                      book_id INTEGER NOT NULL,
                      member_id INTEGER NOT NULL,

                      issue_date DATE NOT NULL,
                      due_date DATE NOT NULL,
                      return_date DATE,
                      fine_amount NUMERIC(10, 2) NOT NULL,

                      CONSTRAINT fk_loan_book
                          FOREIGN KEY (book_id)
                              REFERENCES book(id),

                      CONSTRAINT fk_loan_member
                          FOREIGN KEY (member_id)
                              REFERENCES member(id)
);