CREATE DATABASE  IF NOT EXISTS `my_anime_list`;
USE `my_anime_list`;

DROP TABLE IF EXISTS `users_roles`;
DROP TABLE IF EXISTS `users_anime`;
DROP TABLE IF EXISTS `reviews`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `anime`;

--
-- User can log in only if activation code is null (account activated)
--
CREATE TABLE `users` (
	`id` int NOT NULL AUTO_INCREMENT,
	`username` varchar(50) UNIQUE NOT NULL,
	`password` char(60) NOT NULL,
	`email` varchar(50) NOT NULL,
	`activation_code` varchar(80) DEFAULT NULL,
	`image` MEDIUMBLOB DEFAULT NULL,
	PRIMARY KEY (`id`)
);

--
-- NOTE: The passwords are encrypted using BCrypt
--
-- Default passwords: 
-- admin: 123
-- bob:   1111
--
INSERT INTO `users` (`username`, `password`, `email`)
	VALUES 
		('admin', '$2a$12$kUawd9Xz7u5lTd.9mrEvQ.Wyg3ft/Z1lyNaw..XZkjZbjcpyXrglC', 'oldman@gmail.com'),
		('bob', '$2a$12$22u3/szAs.3aq1/gkyVUfem929OOxqmPTX9S10aOVQKtWh4sp3TR6', 'bob@gmail.com');

CREATE TABLE `roles` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	PRIMARY KEY (`id`)
);

INSERT INTO `roles` (name)
	VALUES
		('ROLE_USER'),
		('ROLE_MANAGER'),
		('ROLE_ADMIN');

CREATE TABLE `users_roles` (
	`user_id` int NOT NULL,
	`role_id` int NOT NULL,
    
	PRIMARY KEY (`user_id`, `role_id`),
    
	CONSTRAINT `fk_users_roles_user_id` FOREIGN KEY (`user_id`) 
		REFERENCES `users`(`id`)
			ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_users_roles_role_id` FOREIGN KEY (`role_id`) 
		REFERENCES `roles`(`id`)
			ON DELETE NO ACTION ON UPDATE NO ACTION
);
        
INSERT INTO `users_roles` (`user_id`, `role_id`)
	VALUES
		('1', '1'),
		('1', '2'),
		('1', '3'),
		('2', '1');
        
CREATE TABLE `reviews` (
	`id` int NOT NULL AUTO_INCREMENT,
	`user_id` int NOT NULL,
	`anime_id` int NOT NULL,
	`content` text NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `fk_reviews_user_id` FOREIGN KEY (`user_id`) 
		REFERENCES `users`(`id`)
			ON DELETE NO ACTION ON UPDATE NO ACTION
);

--
-- 41467 -> Bleach: Sennen Kessen-hen, Top 1 in the rank list.
-- 1     -> Cowboy Bebop
--

INSERT INTO `reviews` (`user_id`, `anime_id`, `content`)
	VALUES
		('2', '41467', 'amazing'),
		('1', '41467', 'nice'),
		('2', '1', 'not bad');

CREATE TABLE `anime` (
	`mal_id` int NOT NULL, -- refers to JikanAPI `mal_id`
	`title` text NOT NULL,
	`image` text NOT NULL,
	PRIMARY KEY (`mal_id`)
);

INSERT INTO `anime` (`mal_id`, `title`, `image`)
	VALUES 
		('1', 'Cowboy Bebop', 'https://cdn.myanimelist.net/images/anime/4/19644.jpg'),
		('41467', 'Bleach: Sennen Kessen-hen', 'https://cdn.myanimelist.net/images/anime/1764/126627.jpg'),
		('11061', 'Hunter x Hunter (2011)', 'https://cdn.myanimelist.net/images/anime/1337/99013.jpg'),
		('9999', 'One Piece 3D: Mugiwara Chase', 'https://cdn.myanimelist.net/images/anime/4/32455.jpg');
	
--
-- Business rules:
-- 	1) User can't score anime if it's in `planning` tab
--	2) Only `watching`/`completed` anime can be added into `favourite` section
--	3) Anime can be added only in one tab at a time (not including `favourite`)
-- 
CREATE TABLE `users_anime` (
	`id` int NOT NULL AUTO_INCREMENT,
	`user_id` int NOT NULL,
	`mal_id` int NOT NULL,
	`score` int DEFAULT NULL,
	`favourite` bool NOT NULL DEFAULT FALSE,
	`watching` bool NOT NULL DEFAULT FALSE,
	`planning` bool NOT NULL DEFAULT FALSE,
	`completed` bool NOT NULL DEFAULT FALSE,
	`on_hold` bool NOT NULL DEFAULT FALSE,
	`dropped` bool NOT NULL DEFAULT FALSE,
    
	PRIMARY KEY (`id`),
	UNIQUE (`user_id`, `mal_id`),
    
	CONSTRAINT `FK_USER_03` FOREIGN KEY (`user_id`) 
		REFERENCES `users`(`id`)
			ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `FK_ANIME` FOREIGN KEY (`mal_id`) 
		REFERENCES `anime`(`mal_id`)
			ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO `users_anime` 
(`user_id`, `mal_id`, `score`, `watching`, `planning`, `dropped`, `completed`, `on_hold`, `favourite`)
	VALUES
		('1', '1', 	'9', 	FALSE,		FALSE,		FALSE,		TRUE,		FALSE,		TRUE),
		('1', '11061', 	'3', 	FALSE,		FALSE,		TRUE,		FALSE,		FALSE,		FALSE),
		('1', '41467', 	'5', 	FALSE,		FALSE,		FALSE,		FALSE,		TRUE,		FALSE),
		('1', '9999', 	'0', 	FALSE,		TRUE,		FALSE,		FALSE,		FALSE,		FALSE),
		('2', '1', 	'1', 	FALSE,		FALSE,		TRUE,		FALSE,		FALSE,		FALSE),
		('2', '11061', 	'10', 	FALSE,		FALSE,		FALSE,		TRUE,		FALSE,		TRUE),
		('2', '41467', 	'8', 	TRUE,		FALSE,		FALSE,		FALSE,		FALSE,		TRUE),
		('2', '9999', 	'3', 	TRUE,		FALSE,		FALSE,		FALSE,		FALSE,		FALSE);

SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM reviews;
SELECT * FROM users_roles
	ORDER BY user_id;
SELECT * FROM users_anime;
SELECT * FROM anime;

SELECT u.id, u.username, u.password, u.email, GROUP_CONCAT(r.name SEPARATOR ', ') AS `roles`
FROM users AS u
	LEFT OUTER JOIN users_roles AS ur
		ON u.id = ur.user_id
	LEFT OUTER JOIN roles AS r
		ON r.id = ur.role_id
GROUP BY u.id;

SELECT r.id, u.id AS `user id`, u.username, r.anime_id AS `anime id`, r.content
FROM reviews AS r
	INNER JOIN users AS u
		ON r.user_id = u.id
ORDER BY 2;

SELECT u.username, a.mal_id, a.title, ua.score, ua.favourite, ua.watching, ua.planning, ua.completed, ua.on_hold, ua.dropped
FROM users AS u
	INNER JOIN users_anime AS ua
		ON u.id = ua.user_id
	INNER JOIN anime AS a
		ON a.mal_id = ua.mal_id
ORDER BY username;
