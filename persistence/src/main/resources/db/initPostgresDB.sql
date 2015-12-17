DROP TABLE IF EXISTS post_user_likes;
DROP TABLE IF EXISTS comments ;
DROP TABLE IF EXISTS post_images;
DROP TABLE IF EXISTS images;
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

CREATE TABLE images (
  id BIGSERIAL,
  img_name VARCHAR NOT NULL,
  CONSTRAINT "img_id" PRIMARY KEY (id)
);

CREATE TABLE post_images (
  post_id BIGINT NOT NULL,
  img_id BIGINT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
  FOREIGN KEY (img_id) REFERENCES images (id) ON DELETE CASCADE,
  CONSTRAINT post_pictures_idx UNIQUE (post_id, img_id)
);

CREATE TABLE comments (
  id BIGSERIAL,
  text VARCHAR(256) NOT NULL, /*type TEXT unlimited*/
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT "comment_id" PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);

CREATE TABLE post_user_likes (
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  CONSTRAINT post_user_likes_idx UNIQUE (post_id, user_id)
)







