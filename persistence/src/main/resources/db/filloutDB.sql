DELETE FROM tag_parameters;
DELETE FROM tags_tag_lines;
DELETE FROM tags;
DELETE FROM tag_types;
DELETE FROM tag_lines;
DELETE FROM clothes;
DELETE FROM post_user_likes;
DELETE FROM comments;
DELETE FROM post_images;
DELETE FROM images;
DELETE FROM posts;
DELETE FROM user_role;
DELETE FROM users;
DELETE FROM roles;
DELETE FROM countries;

ALTER SEQUENCE tag_parameters_id_seq RESTART WITH 1;
ALTER SEQUENCE tags_id_seq RESTART WITH 1;
ALTER SEQUENCE tag_types_id_seq RESTART WITH 1;
ALTER SEQUENCE tag_lines_id_seq RESTART WITH 1;
ALTER SEQUENCE clothes_id_seq RESTART WITH 1;
ALTER SEQUENCE comments_id_seq RESTART WITH 1;
ALTER SEQUENCE images_id_seq RESTART WITH 1;
ALTER SEQUENCE posts_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE roles_id_seq RESTART WITH 1;
ALTER SEQUENCE countries_id_seq RESTART WITH 1;

INSERT INTO countries (name) VALUES
  ('Afghanistan'),
  ('Albania'),
  ('Algeria'),
  ('Andorra'),
  ('Angola'),
  ('Antigua and Barbuda'),
  ('Argentina'),
  ('Armenia'),
  ('Aruba'),
  ('Australia'),
  ('Austria'),
  ('Azerbaijan'),
  ('Bahamas, The'),
  ('Bahrain'),
  ('Bangladesh'),
  ('Barbados'),
  ('Belarus'),
  ('Belgium'),
  ('Belize'),
  ('Benin'),
  ('Bhutan'),
  ('Bolivia'),
  ('Bosnia and Herzegovina'),
  ('Botswana'),
  ('Brazil'),
  ('Brunei'),
  ('Bulgaria'),
  ('Burkina Faso'),
  ('Burma'),
  ('Burundi'),
  ('Cambodia'),
  ('Cameroon'),
  ('Canada'),
  ('Cape Verde'),
  ('Central African Republic'),
  ('Chad'),
  ('Chile'),
  ('China'),
  ('Colombia'),
  ('Comoros'),
  ('Congo, Democratic Republic of the'),
  ('Congo, Republic of the'),
  ('Costa Rica'),
  ('Cote d''Ivoire'),
  ('Croatia'),
  ('Cuba'),
  ('Curacao'),
  ('Cyprus'),
  ('Czech Republic'),
  ('Denmark'),
  ('Djibouti'),
  ('Dominica'),
  ('Dominican Republic'),
  ('East Timor (see Timor-Leste)'),
  ('Ecuador'),
  ('Egypt'),
  ('El Salvador'),
  ('Equatorial Guinea'),
  ('Eritrea'),
  ('Estonia'),
  ('Ethiopia'),
  ('Fiji'),
  ('Finland'),
  ('France'),
  ('Gabon'),
  ('Gambia, The'),
  ('Georgia'),
  ('Germany'),
  ('Ghana'),
  ('Greece'),
  ('Grenada'),
  ('Guatemala'),
  ('Guinea'),
  ('Guinea-Bissau'),
  ('Guyana'),
  ('Haiti'),
  ('Holy See'),
  ('Honduras'),
  ('Hong Kong'),
  ('Hungary'),
  ('Iceland'),
  ('India'),
  ('Indonesia'),
  ('Iran'),
  ('Iraq'),
  ('Ireland'),
  ('Israel'),
  ('Italy'),
  ('Jamaica'),
  ('Japan'),
  ('Jordan'),
  ('Kazakhstan'),
  ('Kenya'),
  ('Kiribati'),
  ('Korea, North'),
  ('Korea, South'),
  ('Kosovo'),
  ('Kuwait'),
  ('Kyrgyzstan'),
  ('Laos'),
  ('Latvia'),
  ('Lebanon'),
  ('Lesotho'),
  ('Liberia'),
  ('Libya'),
  ('Liechtenstein'),
  ('Lithuania'),
  ('Luxembourg'),
  ('Macau'),
  ('Macedonia'),
  ('Madagascar'),
  ('Malawi'),
  ('Malaysia'),
  ('Maldives'),
  ('Mali'),
  ('Malta'),
  ('Marshall Islands'),
  ('Mauritania'),
  ('Mauritius'),
  ('Mexico'),
  ('Micronesia'),
  ('Moldova'),
  ('Monaco'),
  ('Mongolia'),
  ('Montenegro'),
  ('Morocco'),
  ('Mozambique'),
  ('Namibia'),
  ('Nauru'),
  ('Nepal'),
  ('Netherlands'),
  ('Netherlands Antilles'),
  ('New Zealand'),
  ('Nicaragua'),
  ('Niger'),
  ('Nigeria'),
  ('North Korea'),
  ('Norway'),
  ('Oman'),
  ('Pakistan'),
  ('Palau'),
  ('Palestinian Territories'),
  ('Panama'),
  ('Papua New Guinea'),
  ('Paraguay'),
  ('Peru'),
  ('Philippines'),
  ('Poland'),
  ('Portugal'),
  ('Qatar'),
  ('Romania'),
  ('Russia'),
  ('Rwanda'),
  ('Saint Kitts and Nevis'),
  ('Saint Lucia'),
  ('Saint Vincent and the Grenadines'),
  ('Samoa'),
  ('San Marino'),
  ('Sao Tome and Principe'),
  ('Saudi Arabia'),
  ('Senegal'),
  ('Serbia'),
  ('Seychelles'),
  ('Sierra Leone'),
  ('Singapore'),
  ('Sint Maarten'),
  ('Slovakia'),
  ('Slovenia'),
  ('Solomon Islands'),
  ('Somalia'),
  ('South Africa'),
  ('South Korea'),
  ('South Sudan'),
  ('Spain '),
  ('Sri Lanka'),
  ('Sudan'),
  ('Suriname'),
  ('Swaziland '),
  ('Sweden'),
  ('Switzerland'),
  ('Syria'),
  ('Taiwan'),
  ('Tajikistan'),
  ('Tanzania'),
  ('Thailand'),
  ('Timor-Leste'),
  ('Togo'),
  ('Tonga'),
  ('Trinidad and Tobago'),
  ('Tunisia'),
  ('Turkey'),
  ('Turkmenistan'),
  ('Tuvalu'),
  ('Uganda'),
  ('Ukraine'),
  ('United Arab Emirates'),
  ('United Kingdom'),
  ('Uruguay'),
  ('Uzbekistan'),
  ('Vanuatu'),
  ('Venezuela'),
  ('Vietnam'),
  ('Yemen'),
  ('Zambia'),
  ('Zimbabwe');

INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO users (login, email, password) VALUES ('login1', 'email1@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');
INSERT INTO users (login, email, password) VALUES ('login2', 'email2@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');
INSERT INTO users (login, email, password) VALUES ('login3', 'email3@example.com', '$2a$10$nMaTdVApgGyalfxJdehKM.7/vfJznBdMqois3Ppw2sarqHpfHSZy6');

INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM users WHERE id = 1), (SELECT id FROM roles WHERE id = 1));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM users WHERE id = 2), (SELECT id FROM roles WHERE id = 1));
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM users WHERE id = 3), (SELECT id FROM roles WHERE id = 1));

INSERT INTO posts (user_id, title, user_post, category, created, publication_time, status) VALUES ( 1 ,'title1', 'what fits me with these pants?', 'QUESTION', '2015-12-16 12:00:00', '2015-12-16 12:00:00', 'PUBLISHED');
INSERT INTO posts (user_id, title, user_post, category, created, publication_time, status) VALUES ( 2 , 'title2', 'what hat does put on?', 'QUESTION', '2015-12-16 12:01:00', '2015-12-16 12:01:00', 'PUBLISHED');
INSERT INTO posts (user_id, title, user_post, category, created, publication_time, status) VALUES ( 3 , 'title3', 'red is cool?', 'QUESTION', '2015-12-16 12:02:00', '2015-12-16 12:02:00', 'PUBLISHED');
INSERT INTO posts (user_id, title, user_post, category, created, publication_time, status) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login1') , 'title1', 'what fits me with these pants? Again', 'POST', '2015-12-16 12:03:00', '2015-12-16 12:03:00', 'PUBLISHED');
INSERT INTO posts (user_id, title, user_post, category, created, publication_time, status) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login2') , 'title2', 'what hat does put on? Again', 'POST', '2015-12-16 12:04:00', '2015-12-16 12:04:00', 'PUBLISHED');
INSERT INTO posts (user_id, title, user_post, category, created, publication_time, status) VALUES ( (SELECT DISTINCT u.id FROM users AS u WHERE u.login = 'login3') , 'title3', 'red is cool? Again', 'POST', '2015-12-16 12:05:00', '2015-12-16 12:05:00', 'PUBLISHED');
INSERT INTO posts (user_id, title, user_post, category, created, publication_time, status) VALUES ( 3 ,'hidden', 'hidden. Again', 'QUESTION', '2015-12-16 12:15:00',  '2015-12-16 12:15:00', 'HIDDEN');
INSERT INTO posts (user_id, title, user_post, category, created, publication_time, status) VALUES ( 3 ,'wait', 'wait', 'QUESTION', '2015-12-16 12:16:00',  '2015-12-16 12:16:00', 'SCHEDULED');

INSERT INTO images (img_name, user_id) VALUES ('1-1kurtka.jpg', 1);
INSERT INTO images (img_name, user_id) VALUES ('2-13235_huge_weman_shoes_0.jpg', 1);
INSERT INTO images (img_name, user_id) VALUES ('3-jeans1.jpg', 1);
INSERT INTO images (img_name, user_id) VALUES ('4-jeans2.jpg', 2);
INSERT INTO images (img_name, user_id) VALUES ('5-kurtka.jpg', 2);
INSERT INTO images (img_name, user_id) VALUES ('6-sapogi.jpeg', 3);

INSERT INTO post_images (post_id, img_id) VALUES (1,1);
INSERT INTO post_images (post_id, img_id) VALUES (1,2);
INSERT INTO post_images (post_id, img_id) VALUES (1,3);
INSERT INTO post_images (post_id, img_id) VALUES (2,4);
INSERT INTO post_images (post_id, img_id) VALUES (2,5);
INSERT INTO post_images (post_id, img_id) VALUES (3,6);
INSERT INTO post_images (post_id, img_id) VALUES (7,1);
INSERT INTO post_images (post_id, img_id) VALUES (8,1);

INSERT INTO comments (text, post_id, user_id, created, available) VALUES ('cool!', 1, 1,'2015-12-17 12:00:00', 'true');
INSERT INTO comments (text, post_id, user_id, created, available) VALUES ('hidden', 1, 1,'2015-12-17 12:00:00', 'false');
INSERT INTO comments (text, post_id, user_id, created, available) VALUES ('amazing!!', 2, 1,'2015-12-17 12:01:00', 'true');
INSERT INTO comments (text, post_id, user_id, created, available) VALUES ('perfect!!!', 3, 2,'2015-12-17 12:02:00', 'true');

INSERT INTO post_user_likes (post_id, user_id) VALUES (1,2);
INSERT INTO post_user_likes (post_id, user_id) VALUES (1,3);
INSERT INTO post_user_likes (post_id, user_id) VALUES (2,3);
INSERT INTO post_user_likes (post_id, user_id) VALUES (3,1);
INSERT INTO post_user_likes (post_id, user_id) VALUES (3,2);

INSERT INTO clothes (name) VALUES
  ('Blazers'),
  ('Cardigans'),
  ('Dresses'),
  ('Intimates'),
  ('Jeans'),
  ('One-Pieces'),
  ('Outerwear'),
  ('Pants'),
  ('Shirts'),
  ('Shorts'),
  ('Skirts'),
  ('Suits'),
  ('Sweaters'),
  ('Swim'),
  ('Tops'),
  ('Vests'),
  ('Boots'),
  ('Dress Shoes'),
  ('Flats'),
  ('Heels'),
  ('Platforms'),
  ('Sandals'),
  ('Sneakers'),
  ('Wedges'),
  ('Bags'),
  ('Belts'),
  ('Cufflinks'),
  ('Eyewear'),
  ('Gloves'),
  ('Hats'),
  ('Jewelry'),
  ('Legwear'),
  ('Scarves'),
  ('Socks'),
  ('Suspenders'),
  ('Ties'),
  ('Umbrellas'),
  ('Wallets'),
  ('Watches');

INSERT INTO tag_lines (IMAGE_ID, CLOTHES_ID) VALUES (1,3);
INSERT INTO tag_lines (IMAGE_ID, CLOTHES_ID) VALUES (1,2);
INSERT INTO tag_lines (IMAGE_ID, CLOTHES_ID) VALUES (2,4);

INSERT INTO TAG_TYPES (TYPE) VALUES ('brand');
INSERT INTO TAG_TYPES (TYPE) VALUES ('store');

INSERT INTO TAGS (VALUE, TAG_TYPE_ID) VALUES ('UNTITLED & CO', 1);
INSERT INTO TAGS (VALUE, TAG_TYPE_ID) VALUES ('karmaloop', 2);
INSERT INTO TAGS (VALUE, TAG_TYPE_ID) VALUES ('Finch', 1);

INSERT INTO TAGS_TAG_LINES (TAG_LINE_ID,TAG_ID) VALUES (1, 1);
INSERT INTO TAGS_TAG_LINES (TAG_LINE_ID,TAG_ID) VALUES (1, 2);
INSERT INTO TAGS_TAG_LINES (TAG_LINE_ID,TAG_ID) VALUES (1, 3);

INSERT INTO TAG_PARAMETERS (VALUE, NAME, TAG_ID) VALUES ('http://untitledandco.com/collections/womens', 'site', 1);
INSERT INTO TAG_PARAMETERS (VALUE, NAME, TAG_ID) VALUES ('http://www.karmaloop.com/product/The-Hey-Ma-Tee-in-White/549232', 'url', 1);
INSERT INTO TAG_PARAMETERS (VALUE, NAME, TAG_ID) VALUES ('http://finchwear.com.ua/', 'site', 1);