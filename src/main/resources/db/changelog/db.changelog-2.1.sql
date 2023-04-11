--liquibase formatted sql

--changeset viacheslav:1
CREATE TABLE IF NOT EXISTS `revision` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `timestamp` BIGINT NOT NULL,
    PRIMARY KEY (`id`)
);

--changeset viacheslav:2
CREATE TABLE IF NOT EXISTS `users_aud` (
    `id` INT,
    `rev` INT REFERENCES `revision` (`id`),
    `revtype` TINYINT,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(60) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `activation_code` VARCHAR(80) DEFAULT NULL,
    `image` LONGBLOB DEFAULT NULL
);
