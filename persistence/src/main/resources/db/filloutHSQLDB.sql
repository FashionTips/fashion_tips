DELETE FROM post_user_likes;
DELETE FROM comments;
DELETE FROM post_images;
DELETE FROM images;
DELETE FROM posts;
DELETE FROM user_role;
DELETE FROM users;
DELETE FROM roles;

ALTER TABLE comments ALTER COLUMN id RESTART WITH 1;
ALTER TABLE images ALTER COLUMN id RESTART WITH 1;
ALTER TABLE posts ALTER COLUMN id RESTART WITH 1;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;
ALTER TABLE roles ALTER COLUMN id RESTART WITH 1;

INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO users (login, email, password) VALUES ('login1', 'email1@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');
INSERT INTO users (login, email, password) VALUES ('login2', 'email2@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');
INSERT INTO users (login, email, password) VALUES ('login3', 'email3@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');

INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM users WHERE id = 1), (SELECT id FROM roles WHERE id = 1));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM users WHERE id = 2), (SELECT id FROM roles WHERE id = 1));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM users WHERE id = 3), (SELECT id FROM roles WHERE id = 1));

INSERT INTO posts (user_id, title, user_post, category, created) VALUES ( 1 ,'title1', 'what fits me with these pants?', 'QUESTION', '2015-12-16 12:00:00');
INSERT INTO posts (user_id, title, user_post, category, created) VALUES ( 2 , 'title2', 'what hat does put on?', 'QUESTION', '2015-12-16 12:01:00');
INSERT INTO posts (user_id, title, user_post, category, created) VALUES ( 3 , 'title3', 'red is cool?', 'QUESTION', '2015-12-16 12:02:00');
INSERT INTO posts (user_id, title, user_post, category, created) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login1') , 'title1', 'what fits me with these pants? Again', 'POST', '2015-12-16 12:03:00');
INSERT INTO posts (user_id, title, user_post, category, created) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login2') , 'title2', 'what hat does put on? Again', 'POST', '2015-12-16 12:04:00');
INSERT INTO posts (user_id, title, user_post, category, created) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login3') , 'title3', 'red is cool? Again', 'POST', '2015-12-16 12:05:00');

INSERT INTO images (img_name) VALUES ('1-1kurtka.jpg');
INSERT INTO images (img_name) VALUES ('2-13235_huge_weman_shoes_0.jpg');
INSERT INTO images (img_name) VALUES ('3-jeans1.jpg');
INSERT INTO images (img_name) VALUES ('4-jeans2.jpg');
INSERT INTO images (img_name) VALUES ('5-kurtka.jpg');
INSERT INTO images (img_name) VALUES ('6-sapogi.jpeg');

INSERT INTO post_images (post_id, img_id) VALUES (1,1);
INSERT INTO post_images (post_id, img_id) VALUES (1,2);
INSERT INTO post_images (post_id, img_id) VALUES (1,3);
INSERT INTO post_images (post_id, img_id) VALUES (2,4);
INSERT INTO post_images (post_id, img_id) VALUES (2,5);
INSERT INTO post_images (post_id, img_id) VALUES (3,6);

INSERT INTO comments (text, post_id, user_id, created) VALUES ('cool!', 1, 1,'2015-12-17 12:00:00');
INSERT INTO comments (text, post_id, user_id, created) VALUES ('amazing!!', 2, 1,'2015-12-17 12:01:00');
INSERT INTO comments (text, post_id, user_id, created) VALUES ('perfect!!!', 3, 2,'2015-12-17 12:02:00');

INSERT INTO post_user_likes (post_id, user_id) VALUES (1,2);
INSERT INTO post_user_likes (post_id, user_id) VALUES (1,3);
INSERT INTO post_user_likes (post_id, user_id) VALUES (2,3);
INSERT INTO post_user_likes (post_id, user_id) VALUES (3,1);
INSERT INTO post_user_likes (post_id, user_id) VALUES (3,2);