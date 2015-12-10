DROP TABLE IF EXISTS post_images;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS posts_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  login VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255)
);
CREATE UNIQUE INDEX login ON users (login);
CREATE UNIQUE INDEX email ON users (email);

CREATE TABLE posts (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  user_id BIGINT NOT NULL,
  title VARCHAR(256) NOT NULL ,
  user_post VARCHAR(256),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category VARCHAR(64),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE roles (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  role VARCHAR(128),
  description VARCHAR(255),
  user_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tags (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  name VARCHAR(64)
);
CREATE UNIQUE INDEX name ON tags (name);

CREATE TABLE posts_tags (
  post_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT posts_tags_idx UNIQUE (post_id, tag_id)
);

CREATE TABLE images (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  img_name VARCHAR(256) NOT NULL
);

CREATE TABLE post_images (
  post_id BIGINT NOT NULL,
  img_id BIGINT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
  FOREIGN KEY (img_id) REFERENCES images (id) ON DELETE CASCADE,
  CONSTRAINT post_pictures_idx UNIQUE (post_id, img_id)
);

CREATE TABLE comments (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  text VARCHAR(256) NOT NULL, /*may be use CLOB or LONGVARCHAR*/
  post_id BIGINT NOT NULL,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE  CASCADE
);
