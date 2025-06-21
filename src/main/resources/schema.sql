CREATE DATABASE IF NOT EXISTS `api-security-example` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE IF NOT EXISTS `tb_permission` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;;


CREATE TABLE IF NOT EXISTS `tb_users` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_name` varchar(255) DEFAULT NULL,
    `full_name` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `account_non_expired` bit(1) DEFAULT NULL,
    `account_non_locked` bit(1) DEFAULT NULL,
    `credentials_non_expired` bit(1) DEFAULT NULL,
    `enabled` bit(1) DEFAULT NULL,
    `active` bit(1) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_name` (`user_name`)
    ) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `tb_user_permission` (
    `id_user` bigint NOT NULL,
    `id_permission` bigint NOT NULL,
    PRIMARY KEY (`id_user`,`id_permission`),
    KEY `fk_user_permission_permission` (`id_permission`),
    CONSTRAINT `fk_user_permission` FOREIGN KEY (`id_user`) REFERENCES `tb_users` (`id`),
    CONSTRAINT `fk_user_permission_permission` FOREIGN KEY (`id_permission`) REFERENCES `tb_permission` (`id`)
    ) ENGINE=InnoDB;