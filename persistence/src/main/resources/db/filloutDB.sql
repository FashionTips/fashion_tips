DELETE FROM users;
DELETE FROM posts;
DELETE FROM roles;

ALTER SEQUENCE posts_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE roles_id_seq RESTART WITH 1;

INSERT INTO users (login, email, password) VALUES ('login1', 'email1@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');
INSERT INTO users (login, email, password) VALUES ('login2', 'email2@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');
INSERT INTO users (login, email, password) VALUES ('login3', 'email3@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');

INSERT INTO posts (user_id, image_path, title, user_post, category) VALUES ( 1 , '$ROOT_DIRECTORY/images/imege1.jpg', 'title1', 'what fits me with these pants?', 'QUESTION');
INSERT INTO posts (user_id, image_path, title, user_post, category) VALUES ( 2 , '$ROOT_DIRECTORY/images/imege2.jpg', 'title2', 'what hat does put on?', 'QUESTION');
INSERT INTO posts (user_id, image_path, title, user_post, category) VALUES ( 3 , '$ROOT_DIRECTORY/images/imege3.jpg', 'title3', 'red is cool?', 'QUESTION');

INSERT INTO posts (user_id, image_path, title, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login1') , '$ROOT_DIRECTORY/images/imege1.jpg', 'title1', 'what fits me with these pants? Again', 'POST');
INSERT INTO posts (user_id, image_path, title, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login2') , '$ROOT_DIRECTORY/images/imege2.jpg', 'title2', 'what hat does put on? Again', 'POST');
INSERT INTO posts (user_id, image_path, title, user_post, category) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login3') , '$ROOT_DIRECTORY/images/imege3.jpg', 'title3', 'red is cool? Again', 'POST');

INSERT INTO roles (role, description, user_id) VALUES ('ROLE_USER', 'test', 1);
INSERT INTO roles (role, description, user_id) VALUES ('ROLE_USER', 'test', 2);
INSERT INTO roles (role, description, user_id) VALUES ('ROLE_USER', 'test', 3);

