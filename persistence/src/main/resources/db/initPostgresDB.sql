DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users ;

CREATE TABLE users (
  id BIGSERIAL,
  login VARCHAR,
  email VARCHAR,
  password VARCHAR,
  CONSTRAINT "user_id" PRIMARY KEY (id)
);

CREATE UNIQUE INDEX login ON users (login);

CREATE UNIQUE INDEX email ON users (email);

CREATE TABLE posts (
  id BIGSERIAL,
  user_id INTEGER,
  image_path VARCHAR(128),
  user_post VARCHAR(256),
  created TIMESTAMP DEFAULT now(),
  CONSTRAINT "post_id" PRIMARY KEY (id)
);