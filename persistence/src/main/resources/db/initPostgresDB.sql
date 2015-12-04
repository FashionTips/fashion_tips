DROP TABLE IF EXISTS posts_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS roles;
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
  user_id BIGINT NOT NULL,
  image_path VARCHAR,
  title VARCHAR NOT NULL ,
  user_post VARCHAR,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  category VARCHAR(64) NOT NULL ,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT "post_id" PRIMARY KEY (id)
);

CREATE TABLE roles (
  id BIGSERIAL,
  role VARCHAR NOT NULL,
  description VARCHAR DEFAULT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT "role_id" PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE tags (
  id BIGSERIAL,
  name VARCHAR,
  CONSTRAINT "tag_id" PRIMARY KEY (id)
);
CREATE UNIQUE INDEX name ON tags (name);

CREATE TABLE posts_tags (
  post_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts (id),
  FOREIGN KEY (tag_id) REFERENCES tags (id),
  CONSTRAINT posts_tags_idx UNIQUE (post_id, tag_id)
);








