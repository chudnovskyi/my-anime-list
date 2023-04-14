CREATE TABLE IF NOT EXISTS `anime_status` (
    `id` INT NOT NULL PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL
);

DELETE FROM `users_roles`;
DELETE FROM `users_anime`;
DELETE FROM `reviews`;
DELETE FROM `anime`;
DELETE FROM `users`;
DELETE FROM `roles`;
DELETE FROM `anime_status`;

INSERT INTO `users` (`username`, `password`, `email`, `activation_code`)
VALUES
    ('dummy', '$2a$12$shqirUNro2awqXU8trF7ruHQoQRTCtkI6d/dz2DTvfOo.wnhGucei', 'dummy@gmail.com', 'code');

INSERT INTO `roles` (`name`)
VALUES
    ('ROLE_USER'),
    ('ROLE_MANAGER'),
    ('ROLE_ADMIN');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
    ((SELECT id FROM users WHERE username = 'dummy'), (SELECT id FROM roles WHERE name = 'ROLE_USER')),
    ((SELECT id FROM users WHERE username = 'dummy'), (SELECT id FROM roles WHERE name = 'ROLE_MANAGER')),
    ((SELECT id FROM users WHERE username = 'dummy'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));

INSERT INTO `reviews` (`user_id`, `anime_id`, `content`)
VALUES
    ((SELECT id FROM users WHERE username = 'dummy'), 1,    'comment1'),
    ((SELECT id FROM users WHERE username = 'dummy'), 1,    'comment3'),
    ((SELECT id FROM users WHERE username = 'dummy'), 9999, 'comment2');

INSERT INTO `anime` (`id`, `title`, `image`)
VALUES
    (1,     'Cowboy Bebop',                 'https://cdn.myanimelist.net/images/anime/4/19644.jpg'),
    (9999,  'One Piece 3D: Mugiwara Chase', 'https://cdn.myanimelist.net/images/anime/4/32455.jpg');

INSERT INTO `anime_status` (`id`, `name`)
VALUES
    (0, 'WATCHING'),
    (1, 'PLANNING'),
    (2, 'FINISHED'),
    (3, 'ON_HOLD'),
    (4, 'DROPPED');

INSERT INTO `users_anime` (`user_id`, `anime_id`, `score`, `status_id`, `favourite`)
VALUES
    ((SELECT id FROM users WHERE username = 'dummy'), (SELECT id FROM anime WHERE title = 'Cowboy Bebop'),                 1, (SELECT id FROM anime_status WHERE name = 'WATCHING'), TRUE),
    ((SELECT id FROM users WHERE username = 'dummy'), (SELECT id FROM anime WHERE title = 'One Piece 3D: Mugiwara Chase'), 2, (SELECT id FROM anime_status WHERE name = 'PLANNING'), FALSE);
