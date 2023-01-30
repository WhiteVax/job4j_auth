CREATE TABLE person
(
    id       SERIAL PRIMARY KEY NOT NULL,
    login    VARCHAR(2000),
    password VARCHAR(2000)
);