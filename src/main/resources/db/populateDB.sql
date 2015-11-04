DELETE FROM comments;
DELETE FROM requests;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

-- 100000
INSERT INTO users (name, email, password) VALUES ('Макс','maks@gmail.com','password');
-- 100001
INSERT INTO users (name, email, password) VALUES ('Юля','yulia@yandex.ru','password');
-- 100002
INSERT INTO users (name, email, password) VALUES ('Аня','anya@ukr.net','password');

-- 100003
INSERT INTO requests (user_id, description, datetime, pic_url)
VALUES (100000,'Купил вчера ботинки в магазине под домом. Какой цвет брюк под них подойдет?','2015-11-01 14:00:00','http://proboty.ru/wp-content/gallery/top-10-zheltyye-botinki/zheltyye-botinki-top-3-inario.jpg');
-- 100004
INSERT INTO requests (user_id, description, datetime, pic_url)
VALUES (100001,'Моя новая юбка. С чем ее можно одеть?','2015-11-01 18:00:00','http://kompkroy.ru/wp-content/uploads/2012/04/yubka-polusolnce-vykroika-model.jpg');
-- 100005
INSERT INTO requests (user_id, description, datetime, pic_url)
VALUES (100001,'Нижнее белье. С чем сочетается?','2015-11-02 15:00:00','http://www.of-md.com/wp-content/uploads/2015/09/219.jpg');
-- 100006
INSERT INTO requests (user_id, description, datetime, pic_url)
VALUES (100002,'Туфельки с новой коллекции. Что скажете?','2015-11-03 10:30:00','http://modnaya.org/uploads/posts/2012-04/1335616617_tufli-na-vysokom-kabluke.jpg');


INSERT INTO comments (request_id, user_id, comment_text, datetime)
VALUES (100003,100001,'Классные туфли!','2015-11-01 18:00:00');
INSERT INTO comments (request_id, user_id, comment_text, datetime)
VALUES (100003,100002,'С джинсами будет круто!!!','2015-11-02 09:00:00');
INSERT INTO comments (request_id, user_id, comment_text, datetime)
VALUES (100004,100000,'Очень элегантная юбка.','2015-11-01 19:00:00');
INSERT INTO comments (request_id, user_id, comment_text, datetime)
VALUES (100005,100002,'Классно на тебе сидит!!','2015-11-02 17:00:00');
INSERT INTO comments (request_id, user_id, comment_text, datetime)
VALUES (100005,100001,'Спасибочки)))','2015-11-03 11:00:00');
INSERT INTO comments (request_id, user_id, comment_text, datetime)
VALUES (100005,100000,'Тебе очень идет ;-)','2015-11-03 12:00:00');
INSERT INTO comments (request_id, user_id, comment_text, datetime)
VALUES (100006,100001,'Очень стильно!','2015-11-03 20:00:00');