DELETE FROM users;
DELETE FROM post;

INSERT INTO users (login, email, password) VALUES ('login1', 'email1@example.com', '1111');
INSERT INTO users (login, email, password) VALUES ('login2', 'email2@example.com', '1111');
INSERT INTO users (login, email, password) VALUES ('login3', 'email3@example.com', '1111');

INSERT INTO post (1, '$ROOT_DIRECTORY/images/imege1.jpg', 'what fits me with these pants?');
INSERT INTO post (2, '$ROOT_DIRECTORY/images/imege2.jpg', 'what hat does put on?');
INSERT INTO post (3, '$ROOT_DIRECTORY/images/imege3.jpg', 'red is cool?');

INSERT INTO posts (user_id, image_path, user_post) VALUES ( 1 , '$ROOT_DIRECTORY/images/imege1.jpg', 'what fits me with these pants? Again');
INSERT INTO posts (user_id, image_path, user_post) VALUES ( 2 , '$ROOT_DIRECTORY/images/imege2.jpg', 'what hat does put on? Again');
INSERT INTO posts (user_id, image_path, user_post) VALUES ( 3 , '$ROOT_DIRECTORY/images/imege3.jpg', 'red is cool? Again');

