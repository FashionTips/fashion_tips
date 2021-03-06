DROP TABLE IF EXISTS outbox_emails;
DROP TABLE IF EXISTS verification_token;
DROP TABLE IF EXISTS tag_parameters;
DROP TABLE IF EXISTS tags_tag_lines;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS tag_types;
DROP TABLE IF EXISTS tag_lines;
DROP TABLE IF EXISTS clothes;
DROP TABLE IF EXISTS post_user_likes;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS user_images;
DROP TABLE IF EXISTS post_images;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users ;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS countries;

CREATE TABLE countries (
  id INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  name VARCHAR(64)
);

CREATE TABLE roles (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  name VARCHAR(64)
);

CREATE TABLE users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  login VARCHAR(255),
  email VARCHAR(255),
  password VARCHAR(255),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  firstName VARCHAR(64),
  lastName VARCHAR(64),
  birthday DATETIME,
  hideAge BOOLEAN,
  gender VARCHAR(8),
  location VARCHAR(64),
  country_id INT,
  occupation VARCHAR(64),
  aboutMe VARCHAR(255),
  instagram VARCHAR(32),
  blogUrl VARCHAR(255),
  websiteUrl VARCHAR(255),
  FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE UNIQUE INDEX login ON users (login);
CREATE UNIQUE INDEX email ON users (email);

CREATE TABLE user_role (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE posts (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  user_id BIGINT NOT NULL,
  title VARCHAR(256) NOT NULL ,
  user_post VARCHAR(256),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  publication_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  category VARCHAR(64),
  status VARCHAR(64),
  is_comments_allowed BOOLEAN DEFAULT TRUE,
  notification_enabled BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE images (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  img_name VARCHAR(256) NOT NULL,
  user_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE post_images (
  post_id BIGINT NOT NULL,
  img_id BIGINT NOT NULL,
  img_order BIGINT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
  FOREIGN KEY (img_id) REFERENCES images (id) ON DELETE CASCADE
);

CREATE TABLE user_images (
  user_id BIGINT NOT NULL,
  img_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (img_id) REFERENCES images (id) ON DELETE CASCADE,
  CONSTRAINT user_pictures_idx UNIQUE (user_id, img_id)
);

CREATE TABLE comments (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  text VARCHAR(256) NOT NULL, /*may be use CLOB or LONGVARCHAR*/
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  available BOOLEAN,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE  CASCADE
);

CREATE TABLE post_user_likes (
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  CONSTRAINT post_user_likes_idx UNIQUE (post_id, user_id)
);

CREATE TABLE clothes (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  name VARCHAR(64) NOT NULL,
  CONSTRAINT clothes_name UNIQUE (name)
);

CREATE UNIQUE INDEX clothes_name ON clothes (name);

CREATE TABLE tag_lines (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  image_id BIGINT NOT NULL,
  clothes_id BIGINT NOT NULL,
  FOREIGN KEY (image_id) REFERENCES images (id) ON DELETE CASCADE,
  FOREIGN KEY (clothes_id) REFERENCES clothes (id) ON DELETE CASCADE
);

CREATE TABLE tag_types (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  type VARCHAR(128) NOT NULL,
  CONSTRAINT tag_type_name UNIQUE (type)
);

CREATE TABLE tags (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  value VARCHAR(128),
  tag_type_id BIGINT NOT NULL,
  FOREIGN KEY (tag_type_id) REFERENCES tag_types(id) ON DELETE CASCADE
);

CREATE TABLE tags_tag_lines (
  tag_line_id BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  FOREIGN KEY (tag_line_id) REFERENCES tag_lines (id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT tags_lines_stores_idx UNIQUE (tag_line_id,tag_id)
);

CREATE TABLE tag_parameters (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  value VARCHAR(128),
  name VARCHAR(128),
  tag_id BIGINT NOT NULL,
  FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE verification_token (
  email VARCHAR(128) NOT NULL,
  token VARCHAR(64) NOT NULL,
  type VARCHAR(64) NOT NULL,
  expired_time TIMESTAMP,
  verified BOOLEAN DEFAULT FALSE,
  CONSTRAINT verification_token_pk UNIQUE (email, type)
);

CREATE TABLE outbox_emails (
  id INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
  email_from VARCHAR(128),
  email_to VARCHAR(128),
  subject VARCHAR(128),
  text VARCHAR(256)
);