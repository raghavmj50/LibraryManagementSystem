CREATE TABLE book (
                      id SERIAL PRIMARY KEY,
                      isbn VARCHAR(13) NOT NULL UNIQUE,
                      title VARCHAR(200) NOT NULL,
                      published_year INTEGER NOT NULL,
                      total_copies INTEGER NOT NULL,
                      available_copies INTEGER NOT NULL,
                      category_id INTEGER NOT NULL,

                      CONSTRAINT fk_book_category
                          FOREIGN KEY (category_id)
                              REFERENCES category(id)
);