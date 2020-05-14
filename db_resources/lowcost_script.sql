-- MySQL Script generated by MySQL Workbench
-- Thu May 14 13:08:40 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema lowcost_schema
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `lowcost_schema` ;

-- -----------------------------------------------------
-- Schema lowcost_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lowcost_schema` DEFAULT CHARACTER SET utf8 ;
USE `lowcost_schema` ;

-- -----------------------------------------------------
-- Table `lowcost_schema`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`user_role` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`user_role` (
  `id` TINYINT NOT NULL,
  `name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`airport_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`airport_user` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`airport_user` (
  `email` VARCHAR(255) NOT NULL,
  `user_password` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `user_role_id` TINYINT NOT NULL DEFAULT 1,
  `balance_amount` DECIMAL(10,2) NOT NULL DEFAULT 0.0,
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_airport_user_user_role1_idx` (`user_role_id` ASC) VISIBLE,
  CONSTRAINT `fk_airport_user_user_role1`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `lowcost_schema`.`user_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`plane`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`plane` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`plane` (
  `model` VARCHAR(255) NOT NULL,
  `places_quantity` INT NOT NULL,
  PRIMARY KEY (`model`),
  UNIQUE INDEX `model_UNIQUE` (`model` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`city` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `country_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`flight`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`flight` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`flight` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(255) NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `plane_model` VARCHAR(255) NOT NULL,
  `default_price` DECIMAL(5,2) NOT NULL,
  `default_luggage_kg` TINYINT NOT NULL,
  `price_for_every_kg_overweight` DECIMAL(5,2) NOT NULL,
  `available_places` INT NOT NULL,
  `from_id` INT NOT NULL,
  `to_id` INT NOT NULL,
  `primary_boarding_right_price` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_flight_country1_idx` (`from_id` ASC) VISIBLE,
  INDEX `fk_flight_country2_idx` (`to_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_flight_country1`
    FOREIGN KEY (`from_id`)
    REFERENCES `lowcost_schema`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_flight_country2`
    FOREIGN KEY (`to_id`)
    REFERENCES `lowcost_schema`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`ticket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`ticket` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`ticket` (
  `ticket_number` BIGINT NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(7,2) NOT NULL,
  `user_email` VARCHAR(255) NOT NULL,
  `purchase_date` TIMESTAMP NOT NULL,
  `passenger_first_name` VARCHAR(255) NOT NULL,
  `passenger_last_name` VARCHAR(255) NOT NULL,
  `passport_number` VARCHAR(9) NOT NULL,
  `luggage_quantity` INT(11) NOT NULL,
  `luggage_price` DECIMAL(5,2) NOT NULL,
  `primary_boarding_right` TINYINT(1) NOT NULL,
  `flight_id` INT NOT NULL,
  PRIMARY KEY (`ticket_number`),
  INDEX `fk_ticket_airport_user1_idx` (`user_email` ASC) VISIBLE,
  UNIQUE INDEX `ticket_number_UNIQUE` (`ticket_number` ASC) VISIBLE,
  INDEX `fk_ticket_flight2_idx` (`flight_id` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_airport_user1`
    FOREIGN KEY (`user_email`)
    REFERENCES `lowcost_schema`.`airport_user` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_flight2`
    FOREIGN KEY (`flight_id`)
    REFERENCES `lowcost_schema`.`flight` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`date_coeff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`date_coeff` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`date_coeff` (
  `from` DATE NOT NULL,
  `to` DATE NOT NULL,
  `coeff` DECIMAL(3,2) NOT NULL,
  `flight_id` INT NOT NULL,
  PRIMARY KEY (`from`, `to`, `flight_id`),
  INDEX `fk_date_coeff_flight1_idx` (`flight_id` ASC) VISIBLE,
  CONSTRAINT `fk_date_coeff_flight1`
    FOREIGN KEY (`flight_id`)
    REFERENCES `lowcost_schema`.`flight` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`place_coeff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`place_coeff` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`place_coeff` (
  `from` INT NOT NULL,
  `to` INT NOT NULL,
  `coeff` DECIMAL(3,2) NOT NULL,
  `flight_id` INT NOT NULL,
  PRIMARY KEY (`from`, `to`, `flight_id`),
  INDEX `fk_free_places_coeff_flight1_idx` (`flight_id` ASC) VISIBLE,
  CONSTRAINT `fk_free_places_coeff_flight1`
    FOREIGN KEY (`flight_id`)
    REFERENCES `lowcost_schema`.`flight` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`flight_has_plane`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`flight_has_plane` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`flight_has_plane` (
  `flight_id` INT NOT NULL,
  `plane_model` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`flight_id`, `plane_model`),
  INDEX `fk_flight_has_plane_plane1_idx` (`plane_model` ASC) VISIBLE,
  INDEX `fk_flight_has_plane_flight1_idx` (`flight_id` ASC) VISIBLE,
  CONSTRAINT `fk_flight_has_plane_flight1`
    FOREIGN KEY (`flight_id`)
    REFERENCES `lowcost_schema`.`flight` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_flight_has_plane_plane1`
    FOREIGN KEY (`plane_model`)
    REFERENCES `lowcost_schema`.`plane` (`model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `lowcost_schema`.`transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `lowcost_schema`.`transactions` ;

CREATE TABLE IF NOT EXISTS `lowcost_schema`.`transactions` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP NOT NULL,
  `amount` DECIMAL(5,2) NOT NULL,
  `from_id` VARCHAR(255) NOT NULL,
  `ticket_number` BIGINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `lowcost_schema`.`user_role`
-- -----------------------------------------------------
START TRANSACTION;
USE `lowcost_schema`;
INSERT INTO `lowcost_schema`.`user_role` (`id`, `name`) VALUES (0, 'admin');
INSERT INTO `lowcost_schema`.`user_role` (`id`, `name`) VALUES (1, 'user');
INSERT INTO `lowcost_schema`.`user_role` (`id`, `name`) VALUES (2, 'guest');

COMMIT;


-- -----------------------------------------------------
-- Data for table `lowcost_schema`.`airport_user`
-- -----------------------------------------------------
START TRANSACTION;
USE `lowcost_schema`;
INSERT INTO `lowcost_schema`.`airport_user` (`email`, `user_password`, `first_name`, `last_name`, `user_role_id`, `balance_amount`) VALUES ('admin@gmail.com', 'admin', 'Admin', 'Admin', 0, DEFAULT);
INSERT INTO `lowcost_schema`.`airport_user` (`email`, `user_password`, `first_name`, `last_name`, `user_role_id`, `balance_amount`) VALUES ('user@gmail.com', 'user', 'User', 'User', 1, DEFAULT);
INSERT INTO `lowcost_schema`.`airport_user` (`email`, `user_password`, `first_name`, `last_name`, `user_role_id`, `balance_amount`) VALUES ('karpuwapower@gmail.com', 'Aliksei', 'Aliaksei', 'Karpilovich', 1, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `lowcost_schema`.`plane`
-- -----------------------------------------------------
START TRANSACTION;
USE `lowcost_schema`;
INSERT INTO `lowcost_schema`.`plane` (`model`, `places_quantity`) VALUES ('boeing 737-700', 137);
INSERT INTO `lowcost_schema`.`plane` (`model`, `places_quantity`) VALUES ('boeing 737-800', 189);

COMMIT;


-- -----------------------------------------------------
-- Data for table `lowcost_schema`.`city`
-- -----------------------------------------------------
START TRANSACTION;
USE `lowcost_schema`;
INSERT INTO `lowcost_schema`.`city` (`id`, `name`, `country_name`) VALUES (1, 'Minsk', 'Belarus');
INSERT INTO `lowcost_schema`.`city` (`id`, `name`, `country_name`) VALUES (2, 'Vilnus', 'Lithvenia');
INSERT INTO `lowcost_schema`.`city` (`id`, `name`, `country_name`) VALUES (3 , 'Amsterdam', 'The Netherlands');

COMMIT;


-- -----------------------------------------------------
-- Data for table `lowcost_schema`.`flight`
-- -----------------------------------------------------
START TRANSACTION;
USE `lowcost_schema`;
INSERT INTO `lowcost_schema`.`flight` (`id`, `number`, `date`, `plane_model`, `default_price`, `default_luggage_kg`, `price_for_every_kg_overweight`, `available_places`, `from_id`, `to_id`, `primary_boarding_right_price`) VALUES (1, '1', '2020-05-06', 'boeing 737-700', 20, 5, 2, 137, 1, 2, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `lowcost_schema`.`date_coeff`
-- -----------------------------------------------------
START TRANSACTION;
USE `lowcost_schema`;
INSERT INTO `lowcost_schema`.`date_coeff` (`from`, `to`, `coeff`, `flight_id`) VALUES ('2020-04-03', '2020-05-06', 1.0, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `lowcost_schema`.`place_coeff`
-- -----------------------------------------------------
START TRANSACTION;
USE `lowcost_schema`;
INSERT INTO `lowcost_schema`.`place_coeff` (`from`, `to`, `coeff`, `flight_id`) VALUES (0, 200, 1.1, 1);

COMMIT;

