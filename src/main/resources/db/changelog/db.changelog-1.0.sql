--liquibase formatted sql

--changeset viacheslav:1
CREATE TABLE IF NOT EXISTS `users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(60) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `activation_code` VARCHAR(80) DEFAULT NULL,
    `image` LONGBLOB DEFAULT NULL,
    PRIMARY KEY (`id`)
);
--rollback DROP TABLE users;

--changeset viacheslav:2
CREATE TABLE IF NOT EXISTS `roles` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
);
--rollback DROP TABLE roles;

--changeset viacheslav:3
CREATE TABLE IF NOT EXISTS `users_roles` (
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,

    PRIMARY KEY (`user_id`, `role_id`),

    CONSTRAINT `fk_users_roles_user_id` FOREIGN KEY (`user_id`)
        REFERENCES `users`(`id`)
            ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_users_roles_role_id` FOREIGN KEY (`role_id`)
        REFERENCES `roles`(`id`)
            ON DELETE NO ACTION ON UPDATE NO ACTION
);
--rollback DROP TABLE users_roles;

--changeset viacheslav:4
CREATE TABLE IF NOT EXISTS `reviews` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `anime_id` INT NOT NULL,
    `content` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_reviews_user_id` FOREIGN KEY (`user_id`)
        REFERENCES `users`(`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
--rollback DROP TABLE reviews;

--changeset viacheslav:5
CREATE TABLE IF NOT EXISTS `anime` (
    `id` INT NOT NULL PRIMARY KEY, -- refers to JikanAPI `mal_id`
    `title` VARCHAR(255) NOT NULL,
    `image` VARCHAR(255) NOT NULL
);
--rollback DROP TABLE anime;

--changeset viacheslav:6
CREATE TABLE IF NOT EXISTS `anime_status` (
    `id` INT NOT NULL PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL
);
--rollback DROP TABLE anime_status;

--changeset viacheslav:7
CREATE TABLE IF NOT EXISTS `users_anime` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `anime_id` INT NOT NULL,
    `status_id` INT NOT NULL,
    `favourite` BIT(1) NOT NULL DEFAULT FALSE,
    `score` INT DEFAULT NULL,

    PRIMARY KEY (`id`),
    UNIQUE (`user_id`, `anime_id`),

    CONSTRAINT `FK_USER_03` FOREIGN KEY (`user_id`)
        REFERENCES `users`(`id`)
            ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_ANIME` FOREIGN KEY (`anime_id`)
        REFERENCES `anime`(`id`)
            ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `FK_STATUS` FOREIGN KEY (`status_id`)
        REFERENCES `anime_status`(`id`)
            ON DELETE NO ACTION ON UPDATE NO ACTION
);
--rollback DROP TABLE users_anime;
