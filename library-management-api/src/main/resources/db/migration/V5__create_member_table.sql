CREATE TABLE member (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        phone VARCHAR(10) NOT NULL,
                        registered_on DATE NOT NULL
);