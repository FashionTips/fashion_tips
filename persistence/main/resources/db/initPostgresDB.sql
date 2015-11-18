DROP TABLE IF EXISTS users ;

CREATE TABLE users (
  id SERIAL,
  login VARCHAR,
  email VARCHAR,
  password VARCHAR,
  CONSTRAINT "user_id" PRIMARY KEY (id)
);

CREATE UNIQUE INDEX login ON users (login);

CREATE UNIQUE INDEX email ON users (email);