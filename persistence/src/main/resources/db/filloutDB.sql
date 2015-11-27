DELETE FROM users;
DELETE FROM posts;

INSERT INTO users (login, email, password) VALUES ('login1', 'email1@example.com', '1111');
INSERT INTO users (login, email, password) VALUES ('login2', 'email2@example.com', '1111');
INSERT INTO users (login, email, password) VALUES ('login3', 'email3@example.com', '1111');
--
-- INSERT INTO category (category_type, category_description) VALUES ('POST', 'Regular post');
-- INSERT INTO category (category_type, category_description) VALUES ('QUESTION', 'Users question about outfits');

INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( 1 , '$ROOT_DIRECTORY/images/imege1.jpg', 'what fits me with these pants?', 'QUESTION');
INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( 2 , '$ROOT_DIRECTORY/images/imege2.jpg', 'what hat does put on?', 'QUESTION');
INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( 3 , '$ROOT_DIRECTORY/images/imege3.jpg', 'red is cool?', 'QUESTION');

INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login1') , '$ROOT_DIRECTORY/images/imege1.jpg', 'what fits me with these pants? Again', 'POST');
INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login2') , '$ROOT_DIRECTORY/images/imege2.jpg', 'what hat does put on? Again', 'POST');
INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login3') , '$ROOT_DIRECTORY/images/imege3.jpg', 'red is cool? Again', 'POST');

