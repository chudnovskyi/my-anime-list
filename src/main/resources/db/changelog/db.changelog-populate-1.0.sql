--liquibase formatted sql

--changeset viacheslav:1
INSERT INTO `users` (`username`, `password`, `email`)
VALUES
    ('admin', '$2a$12$kUawd9Xz7u5lTd.9mrEvQ.Wyg3ft/Z1lyNaw..XZkjZbjcpyXrglC', 'oldman@gmail.com'),
    ('bob', '$2a$12$22u3/szAs.3aq1/gkyVUfem929OOxqmPTX9S10aOVQKtWh4sp3TR6', 'bob@gmail.com');

--changeset viacheslav:2
INSERT INTO `roles` (name)
VALUES
    ('ROLE_USER'),
    ('ROLE_MANAGER'),
    ('ROLE_ADMIN');

--changeset viacheslav:3
INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
    ('1', '1'),
    ('1', '2'),
    ('1', '3'),
    ('2', '1');

--changeset viacheslav:4
INSERT INTO `reviews` (`user_id`, `anime_id`, `content`)
VALUES
    ('2', '41467', 'amazing'),
    ('1', '41467', 'nice'),
    ('2', '1', 'not bad');

--changeset viacheslav:5
INSERT INTO `anime` (`id`, `title`, `image`)
VALUES
    ('1', 'Cowboy Bebop', 'https://cdn.myanimelist.net/images/anime/4/19644.jpg'),
    ('41467', 'Bleach: Sennen Kessen-hen', 'https://cdn.myanimelist.net/images/anime/1764/126627.jpg'),
    ('11061', 'Hunter x Hunter (2011)', 'https://cdn.myanimelist.net/images/anime/1337/99013.jpg'),
    ('9999', 'One Piece 3D: Mugiwara Chase', 'https://cdn.myanimelist.net/images/anime/4/32455.jpg');

--changeset viacheslav:6
INSERT INTO `anime_status` (`id`, `name`)
VALUES
    ('0', 'WATCHING'),
    ('1', 'PLANNING'),
    ('2', 'FINISHED'),
    ('3', 'ON_HOLD'),
    ('4', 'DROPPED');

--changeset viacheslav:7
INSERT INTO `users_anime`
(`user_id`, `anime_id`, `score`, `status_id`, `favourite`)
VALUES
    ('1', '1',		'9',	'2', TRUE),
    ('1', '11061',	'3',	'4', FALSE),
    ('1', '41467',	'5',	'3', FALSE),
    ('1', '9999',	'0',	'1', FALSE),
    ('2', '1',		'1',	'4', FALSE),
    ('2', '11061',	'10',	'2', TRUE),
    ('2', '41467',	'8',	'0', TRUE),
    ('2', '9999',	'3',	'0', FALSE);
