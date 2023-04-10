CREATE TABLE IF NOT EXISTS `anime_status` (
    `id` INT NOT NULL PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL
);


INSERT INTO `users` (`id`, `username`, `password`, `email`)
VALUES
    (1, 'admin', '$2a$12$kUawd9Xz7u5lTd.9mrEvQ.Wyg3ft/Z1lyNaw..XZkjZbjcpyXrglC', 'oldman@gmail.com'),
    (2, 'bob', '$2a$12$22u3/szAs.3aq1/gkyVUfem929OOxqmPTX9S10aOVQKtWh4sp3TR6', 'bob@gmail.com');

INSERT INTO `roles` (`id`, `name`)
VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_MANAGER'),
    (3, 'ROLE_ADMIN');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
    ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_USER')),
    ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_MANAGER')),
    ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
    ((SELECT id FROM users WHERE username = 'bob'),   (SELECT id FROM roles WHERE name = 'ROLE_USER'));

INSERT INTO `reviews` (`id`, `user_id`, `anime_id`, `content`)
VALUES
    (1, (SELECT id FROM users WHERE username = 'bob'),   41467, 'amazing'),
    (2, (SELECT id FROM users WHERE username = 'admin'), 41467, 'nice'),
    (3, (SELECT id FROM users WHERE username = 'bob'),   1,     'not bad');

INSERT INTO `anime` (`id`, `title`, `image`)
VALUES
    (1,     'Cowboy Bebop',                 'https://cdn.myanimelist.net/images/anime/4/19644.jpg'),
    (41467, 'Bleach: Sennen Kessen-hen',    'https://cdn.myanimelist.net/images/anime/1764/126627.jpg'),
    (11061, 'Hunter x Hunter (2011)',       'https://cdn.myanimelist.net/images/anime/1337/99013.jpg'),
    (9999,  'One Piece 3D: Mugiwara Chase', 'https://cdn.myanimelist.net/images/anime/4/32455.jpg');

INSERT INTO `anime_status` (`id`, `name`)
VALUES
    (0, 'WATCHING'),
    (1, 'PLANNING'),
    (2, 'FINISHED'),
    (3, 'ON_HOLD'),
    (4, 'DROPPED');

INSERT INTO `users_anime` (`id`, `user_id`, `anime_id`, `score`, `status_id`, `favourite`)
VALUES
    (1, (SELECT id FROM users WHERE username = 'bob'),   (SELECT id FROM anime WHERE title = 'Cowboy Bebop'),                 9,  (SELECT id FROM anime_status WHERE name = 'FINISHED'), TRUE),
    (2, (SELECT id FROM users WHERE username = 'bob'),   (SELECT id FROM anime WHERE title = 'Hunter x Hunter (2011)'),       3,  (SELECT id FROM anime_status WHERE name = 'DROPPED'),  FALSE),
    (3, (SELECT id FROM users WHERE username = 'bob'),   (SELECT id FROM anime WHERE title = 'Bleach: Sennen Kessen-hen'),    5,  (SELECT id FROM anime_status WHERE name = 'ON_HOLD'),  FALSE),
    (4, (SELECT id FROM users WHERE username = 'bob'),   (SELECT id FROM anime WHERE title = 'One Piece 3D: Mugiwara Chase'), 0,  (SELECT id FROM anime_status WHERE name = 'PLANNING'), FALSE),
    (5, (SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM anime WHERE title = 'Cowboy Bebop'),                 1,  (SELECT id FROM anime_status WHERE name = 'DROPPED'),  FALSE),
    (6, (SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM anime WHERE title = 'Hunter x Hunter (2011)'),       10, (SELECT id FROM anime_status WHERE name = 'FINISHED'), TRUE),
    (7, (SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM anime WHERE title = 'Bleach: Sennen Kessen-hen'),    8,  (SELECT id FROM anime_status WHERE name = 'WATCHING'), TRUE),
    (8, (SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM anime WHERE title = 'One Piece 3D: Mugiwara Chase'), 3,  (SELECT id FROM anime_status WHERE name = 'WATCHING'), FALSE);
