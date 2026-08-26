CREATE TABLE book_author (
                             book_id INTEGER NOT NULL,
                             author_id INTEGER NOT NULL,

                             CONSTRAINT fk_book_author_book
                                 FOREIGN KEY (book_id)
                                     REFERENCES book(id),

                             CONSTRAINT fk_book_author_author
                                 FOREIGN KEY (author_id)
                                     REFERENCES author(id),

                             PRIMARY KEY (book_id, author_id)
);