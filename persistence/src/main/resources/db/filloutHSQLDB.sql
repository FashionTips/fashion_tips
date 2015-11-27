DELETE FROM users;
DELETE FROM posts;

ALTER TABLE users ALTER COLUMN id RESTART WITH 1;
-- ALTER TABLE post ALTER COLUMN id RESTART WITH 1;

INSERT INTO users (login, email, password) VALUES ('login1', 'email1@example.com', '1111');
INSERT INTO users (login, email, password) VALUES ('login2', 'email2@example.com', '1111');
INSERT INTO users (login, email, password) VALUES ('login3', 'email3@example.com', '1111');

INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( 1 , '$ROOT_DIRECTORY/images/imege1.jpg', 'what fits me with these pants?', 'Question');
INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( 2 , '$ROOT_DIRECTORY/images/imege2.jpg', 'what hat does put on?', 'Question');
INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( 3 , '$ROOT_DIRECTORY/images/imege3.jpg', 'red is cool?', 'Question');

INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login1') , '$ROOT_DIRECTORY/images/imege1.jpg', 'what fits me with these pants? Again', 'Post');
INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login2') , '$ROOT_DIRECTORY/images/imege2.jpg', 'what hat does put on? Again', 'Post');
INSERT INTO posts (user_id, image_path, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login3') , '$ROOT_DIRECTORY/images/imege3.jpg', 'red is cool? Again', 'Post');

