DELETE FROM post_images;
DELETE FROM images;
DELETE FROM posts;
DELETE FROM users;

ALTER SEQUENCE images_id_seq RESTART WITH 1;
ALTER SEQUENCE posts_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 1;

INSERT INTO users (login, email, password) VALUES ('login1', 'email1@example.com', '1111');
INSERT INTO users (login, email, password) VALUES ('login2', 'email2@example.com', '1111');
INSERT INTO users (login, email, password) VALUES ('login3', 'email3@example.com', '1111');

INSERT INTO posts (user_id, title, user_post, category) VALUES ( 1 ,'title1', 'what fits me with these pants?', 'QUESTION');
INSERT INTO posts (user_id, title, user_post, category) VALUES ( 2 , 'title2', 'what hat does put on?', 'QUESTION');
INSERT INTO posts (user_id, title, user_post, category) VALUES ( 3 , 'title3', 'red is cool?', 'QUESTION');

INSERT INTO posts (user_id, title, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login1') , 'title1', 'what fits me with these pants? Again', 'POST');
INSERT INTO posts (user_id, title, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login2') , 'title2', 'what hat does put on? Again', 'POST');
INSERT INTO posts (user_id, title, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login3') , 'title3', 'red is cool? Again', 'POST');

INSERT INTO images (img_name) VALUES ('1-1kurtka.jpg');
INSERT INTO images (img_name) VALUES ('2-13235_высокие_женские_сапоги_0.jpg');
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

