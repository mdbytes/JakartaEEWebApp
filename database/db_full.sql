-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema site_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema site_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `site_db`;
CREATE SCHEMA `site_db`;
USE `site_db`;

-- -----------------------------------------------------
-- Table `site_db`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `site_db`.`roles`
(
    `id`   INT          NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `site_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `site_db`.`users`
(
    `id`             INT          NOT NULL AUTO_INCREMENT,
    `city_address`   VARCHAR(255) NULL DEFAULT NULL,
    `created_on`     DATETIME(6)  NULL DEFAULT NULL,
    `email_address`  VARCHAR(255) NOT NULL,
    `first_name`     VARCHAR(255) NOT NULL,
    `last_name`      VARCHAR(255) NOT NULL,
    `password`       VARCHAR(255) NOT NULL,
    `phone_number`   VARCHAR(255) NOT NULL,
    `state_address`  VARCHAR(255) NULL DEFAULT NULL,
    `street_address` VARCHAR(255) NULL DEFAULT NULL,
    `updated_on`     DATETIME(6)  NULL DEFAULT NULL,
    `username`       VARCHAR(255) NOT NULL,
    `zip_code`       VARCHAR(255) NULL DEFAULT NULL,
    `is_active`      TINYINT      NULL,
    `birth_date`     DATE         NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = MEMORY
    AUTO_INCREMENT = 27
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `site_db`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `site_db`.`user_roles`
(
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Process Inserts
-- -----------------------------------------------------

INSERT INTO roles
    (id, name)
VALUES (1, "USER"),
       (2, "ADMIN"),
       (3, "EXECUTIVE");

-- -----------------------------------------------------
-- Process stored procedures
-- -----------------------------------------------------

DROP PROCEDURE IF EXISTS sp_create_role;

delimiter //

CREATE PROCEDURE sp_create_role(
    IN _role VARCHAR(255)
)
BEGIN
    INSERT INTO roles
        (name)
    VALUES (_role);
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_create_user;

delimiter //

CREATE PROCEDURE sp_create_user(
    IN _FirstName VARCHAR(255),
    IN _LastName VARCHAR(255),
    IN _StreetAddress VARCHAR(255),
    IN _City VARCHAR(255),
    IN _State VARCHAR(255),
    IN _ZipCode VARCHAR(255),
    IN _Email VARCHAR(255),
    IN _Password VARCHAR(255),
    IN _PhoneNumber VARCHAR(255),
    IN _username VARCHAR(255),
    IN _IsActive TINYINT,
    IN _BirthDate DATE,
    IN _CreatedOn DATETIME,
    IN _UpdatedOn DATETIME
)
BEGIN
    INSERT INTO users
    (first_name, last_name, street_address, city_address, state_address, zip_code, email_address,
     password, phone_number, username, is_active, birth_date, created_on, updated_on)
    VALUES (_FirstName, _LastName, _StreetAddress, _City, _State, _ZipCode, _Email,
            _Password, _PhoneNumber, _username, _IsActive, _BirthDate, _CreatedOn, _UpdatedOn);
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_create_user_role;

delimiter //

CREATE PROCEDURE sp_create_user_role(
    IN _UserId INT,
    IN _RoleId INT
)
BEGIN
    INSERT INTO user_roles
        (user_id, role_id)
    VALUES (_UserId, _RoleId);
END//

delimiter ;


# CRUD procedures for all tables

# Create objects

DROP PROCEDURE IF EXISTS sp_select_all_roles;

delimiter //

CREATE PROCEDURE sp_select_all_roles(
)
BEGIN
    SELECT id, name
    FROM roles;
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_select_all_role_by_id;

delimiter //

CREATE PROCEDURE sp_select_all_role_by_id(
    IN _roleId INT
)
BEGIN
    SELECT id, name
    FROM roles
    WHERE id = _roleId;
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_select_all_user_roles;

delimiter //

CREATE PROCEDURE sp_select_all_user_roles(
)
BEGIN
    SELECT user_id, role_id
    FROM user_roles;
END//

delimiter ;


DROP PROCEDURE IF EXISTS sp_select_roles_by_user_id;

delimiter //

CREATE PROCEDURE sp_select_roles_by_user_id(
    IN _UserId INT
)
BEGIN
    SELECT ur.role_id, r.name
    FROM user_roles ur
             JOIN roles r
                  ON r.id = ur.role_id
    WHERE ur.user_id = _UserId;
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_select_all_users;

delimiter //

CREATE PROCEDURE sp_select_all_users(
)
BEGIN
    SELECT id,
           first_name,
           last_name,
           street_address,
           city_address,
           state_address,
           zip_code,
           email_address,
           password,
           phone_number,
           username,
           is_active,
           birth_date,
           created_on,
           updated_on
    FROM users;
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_select_user_by_id;

delimiter //

CREATE PROCEDURE sp_select_user_by_id(
    IN _UserId INT
)
BEGIN
    SELECT id,
           first_name,
           last_name,
           street_address,
           city_address,
           state_address,
           zip_code,
           email_address,
           password,
           phone_number,
           username,
           is_active,
           birth_date,
           created_on,
           updated_on
    FROM users
    WHERE id = _UserId;
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_select_user_by_email;

delimiter //

CREATE PROCEDURE sp_select_user_by_email(
    IN _Email VARCHAR(255)
)
BEGIN
    SELECT id,
           first_name,
           last_name,
           street_address,
           city_address,
           state_address,
           zip_code,
           email_address,
           password,
           phone_number,
           username,
           is_active,
           birth_date,
           created_on,
           updated_on
    FROM users
    WHERE email_address = _Email;
END//

delimiter ;

# CRUD procedures for all tables

# Create objects

DROP PROCEDURE IF EXISTS sp_update_role;

delimiter //

CREATE PROCEDURE sp_update_role(
    IN _role_id int,
    IN _role VARCHAR(255)
)
BEGIN
    UPDATE roles
    SET name = _role
    WHERE id = _role_id;
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_update_user;

delimiter //

CREATE PROCEDURE sp_update_user(
    IN _UserId INT,
    IN _FirstName VARCHAR(255),
    IN _LastName VARCHAR(255),
    IN _StreetAddress VARCHAR(255),
    IN _City VARCHAR(255),
    IN _State VARCHAR(255),
    IN _ZipCode VARCHAR(255),
    IN _Email VARCHAR(255),
    IN _Password VARCHAR(255),
    IN _PhoneNumber VARCHAR(255),
    IN _username VARCHAR(255),
    IN _IsActive TINYINT,
    IN _BirthDate DATE,
    IN _CreatedOn DATETIME,
    IN _UpdatedOn DATETIME
)
BEGIN
    UPDATE users
    SET first_name=_FirstName,
        last_name=_LastName,
        street_address=_StreetAddress,
        city_address=_City,
        state_address=_State,
        zip_code=_ZipCode,
        email_address=_Email,
        password=_Password,
        phone_number=_PhoneNumber,
        username=_username,
        is_active=_IsActive,
        birth_date=_BirthDate,
        created_on=_CreatedOn,
        updated_on=_UpdatedOn
    WHERE id = _UserId;

END//

delimiter ;


# CRUD procedures for all tables

# Create objects

DROP PROCEDURE IF EXISTS sp_delete_role;

delimiter //

CREATE PROCEDURE sp_delete_role(
    IN _role_id int
)
BEGIN
    DELETE
    FROM roles
    WHERE id = _role_id;
END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_delete_user;

delimiter //

CREATE PROCEDURE sp_delete_user(
    IN _UserId INT
)
BEGIN
    DELETE
    FROM users
    WHERE id = _UserId;

END//

delimiter ;

DROP PROCEDURE IF EXISTS sp_delete_user_role;

delimiter //

CREATE PROCEDURE sp_delete_user_role(
    IN _UserId INT,
    IN _RoleId INT
)
BEGIN
    DELETE
    FROM user_roles
    WHERE user_id = _UserId
      AND role_id = _RoleId;

END//

delimiter ;
