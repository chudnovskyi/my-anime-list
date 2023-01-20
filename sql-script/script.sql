CREATE DATABASE  IF NOT EXISTS `my-anime-list`;
USE `my-anime-list`;

DROP TABLE IF EXISTS `users_anime`;
DROP TABLE IF EXISTS `users_roles`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;

CREATE TABLE `user` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`username` varchar(50) UNIQUE NOT NULL,
    `password` char(60) NOT NULL,
	`email` varchar(50) NOT NULL,
	PRIMARY KEY (`id`)
);

--
-- NOTE: The passwords are encrypted using BCrypt
--
-- Default passwords: 
-- admin: 123
-- bob:   1111
--
INSERT INTO `user` (`username`, `password`, `email`)
	VALUES 
		('admin', '$2a$12$kUawd9Xz7u5lTd.9mrEvQ.Wyg3ft/Z1lyNaw..XZkjZbjcpyXrglC', 'oldman@gmail.com'),
        ('bob', '$2a$12$22u3/szAs.3aq1/gkyVUfem929OOxqmPTX9S10aOVQKtWh4sp3TR6', 'bob@gmail.com');

CREATE TABLE `role` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `role` (name)
	VALUES
		('ROLE_USER'),
        ('ROLE_MANAGER'),
        ('ROLE_ADMIN');

CREATE TABLE `users_roles` (
	`user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    
    PRIMARY KEY (`user_id`, `role_id`),
    
    CONSTRAINT `FK_USER` FOREIGN KEY (`user_id`) 
		REFERENCES `user`(`id`)
			ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
		REFERENCES `role`(`id`)
			ON DELETE NO ACTION ON UPDATE NO ACTION
);
        
INSERT INTO `users_roles` (`user_id`, `role_id`)
	VALUES
		('1', '1'),
        ('1', '2'),
        ('1', '3'),
        ('2', '1');

SELECT * FROM user;
SELECT * FROM role;
SELECT * FROM users_roles
	ORDER BY user_id;

SELECT u.id, u.username, u.password, u.email, GROUP_CONCAT(r.name SEPARATOR ', ') AS `roles`
FROM user AS u
	LEFT OUTER JOIN users_roles AS ur
		ON u.id = ur.user_id
	LEFT OUTER JOIN role AS r
		ON r.id = ur.role_id
GROUP BY u.id;