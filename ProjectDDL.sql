-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Project
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Project` ;

-- -----------------------------------------------------
-- Schema Project
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Project` DEFAULT CHARACTER SET utf8 ;
USE `Project` ;

-- -----------------------------------------------------
-- Table `Project`.`DisasterEvent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`DisasterEvent` ;

CREATE TABLE IF NOT EXISTS `Project`.`DisasterEvent` (
  `DisasterEventId` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(45) NOT NULL,
  `Location` INT UNSIGNED NOT NULL,
  `StartDate` DATETIME NOT NULL,
  PRIMARY KEY (`DisasterEventId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`Location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`Location` ;

CREATE TABLE IF NOT EXISTS `Project`.`Location` (
  `LocationId` INT NOT NULL AUTO_INCREMENT,
  `Lattitude` FLOAT NOT NULL,
  `Longitude` FLOAT NOT NULL,
  `StreetNum` INT NOT NULL,
  `Street` VARCHAR(32) NOT NULL,
  `City` VARCHAR(20) NOT NULL,
  `Zipcode` INT NOT NULL,
  PRIMARY KEY (`LocationId`),
  UNIQUE INDEX `LattitudeLongitude` (`Lattitude` ASC, `Longitude` ASC) VISIBLE,
  UNIQUE INDEX `Address` (`Zipcode` ASC, `City` ASC, `Street` ASC, `StreetNum` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`User` ;

CREATE TABLE IF NOT EXISTS `Project`.`User` (
  `UserId` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(25) NOT NULL,
  `Password` VARCHAR(32) NOT NULL,
  `FirstName` VARCHAR(25) NOT NULL,
  `LastName` VARCHAR(25) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Phone` VARCHAR(15) NOT NULL,
  `LastLogin` DATETIME NULL,
  `FailedLoginAttempts` INT UNSIGNED NOT NULL,
  `LocationId` INT NOT NULL,
  PRIMARY KEY (`UserId`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE,
  INDEX `LocationIdUser_idx` (`LocationId` ASC) VISIBLE,
  CONSTRAINT `LocationIdUser`
    FOREIGN KEY (`LocationId`)
    REFERENCES `Project`.`Location` (`LocationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`Product` ;

CREATE TABLE IF NOT EXISTS `Project`.`Product` (
  `ProductId` INT NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ProductId`),
  UNIQUE INDEX `Type_UNIQUE` (`Type` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`Request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`Request` ;

CREATE TABLE IF NOT EXISTS `Project`.`Request` (
  `RequestId` INT NOT NULL AUTO_INCREMENT,
  `DisasterEventId` INT NOT NULL,
  `UserId` INT NOT NULL,
  `ProductId` INT NOT NULL,
  `QuantityRequested` INT NOT NULL,
  `QuantityFulfilled` INT NOT NULL,
  `Expired` TINYINT NOT NULL,
  `NeededByDate` DATETIME NOT NULL,
  `LocationId` INT NOT NULL,
  PRIMARY KEY (`RequestId`, `Expired`),
  INDEX `DisasterEventId_idx` (`DisasterEventId` ASC) VISIBLE,
  INDEX `UserId_idx` (`UserId` ASC) VISIBLE,
  INDEX `ProductId_idx` (`ProductId` ASC) VISIBLE,
  INDEX `LocationIdRequest_idx` (`LocationId` ASC) VISIBLE,
  CONSTRAINT `DisasterEventIdRequest`
    FOREIGN KEY (`DisasterEventId`)
    REFERENCES `Project`.`DisasterEvent` (`DisasterEventId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `UserIdRequest`
    FOREIGN KEY (`UserId`)
    REFERENCES `Project`.`User` (`UserId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ProductIdRequest`
    FOREIGN KEY (`ProductId`)
    REFERENCES `Project`.`Product` (`ProductId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `LocationIdRequest`
    FOREIGN KEY (`LocationId`)
    REFERENCES `Project`.`Location` (`LocationId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`Response`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`Response` ;

CREATE TABLE IF NOT EXISTS `Project`.`Response` (
  `ResponseId` INT NOT NULL AUTO_INCREMENT,
  `QuantitySent` INT NOT NULL,
  `RequestId` INT NOT NULL,
  `UserId` INT NOT NULL,
  `ProvidedByDate` DATETIME NOT NULL,
  PRIMARY KEY (`ResponseId`),
  INDEX `RequestId_idx` (`RequestId` ASC) VISIBLE,
  INDEX `UserId_idx` (`UserId` ASC) VISIBLE,
  CONSTRAINT `RequestIdResponse`
    FOREIGN KEY (`RequestId`)
    REFERENCES `Project`.`Request` (`RequestId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `UserIdResponse`
    FOREIGN KEY (`UserId`)
    REFERENCES `Project`.`User` (`UserId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`CallCenter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`CallCenter` ;

CREATE TABLE IF NOT EXISTS `Project`.`CallCenter` (
  `CallCentedId` INT NOT NULL AUTO_INCREMENT,
  `Location` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`CallCentedId`),
  UNIQUE INDEX `Location_UNIQUE` (`Location` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`Employee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`Employee` ;

CREATE TABLE IF NOT EXISTS `Project`.`Employee` (
  `EmployeeId` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(25) NOT NULL,
  `LastName` VARCHAR(25) NOT NULL,
  `Username` VARCHAR(25) NOT NULL,
  `Password` VARCHAR(32) NOT NULL,
  `CallCenterId` INT NOT NULL,
  `Admin` TINYINT NOT NULL,
  PRIMARY KEY (`EmployeeId`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE,
  INDEX `CallCenterId_idx` (`CallCenterId` ASC) VISIBLE,
  CONSTRAINT `CallCenterIdEmployee`
    FOREIGN KEY (`CallCenterId`)
    REFERENCES `Project`.`CallCenter` (`CallCentedId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`Donation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`Donation` ;

CREATE TABLE IF NOT EXISTS `Project`.`Donation` (
  `DonationId` INT NOT NULL AUTO_INCREMENT,
  `ProductId` INT NOT NULL,
  `Amount` INT NOT NULL,
  `UserId` INT NOT NULL,
  PRIMARY KEY (`DonationId`),
  INDEX `UserId_idx` (`UserId` ASC) VISIBLE,
  CONSTRAINT `ProductIdDonation`
    FOREIGN KEY (`ProductId`)
    REFERENCES `Project`.`Product` (`ProductId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `UserIdDonation`
    FOREIGN KEY (`UserId`)
    REFERENCES `Project`.`User` (`UserId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`.`StoredProduct`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project`.`StoredProduct` ;

CREATE TABLE IF NOT EXISTS `Project`.`StoredProduct` (
  `StoredProductId` INT NOT NULL AUTO_INCREMENT,
  `Quantity` INT NOT NULL,
  `ProductId` INT NOT NULL,
  PRIMARY KEY (`StoredProductId`),
  UNIQUE INDEX `ProductId_UNIQUE` (`ProductId` ASC) VISIBLE,
  CONSTRAINT `ProductIdStored`
    FOREIGN KEY (`ProductId`)
    REFERENCES `Project`.`Product` (`ProductId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `Project` ;

-- -----------------------------------------------------
-- procedure CREATE_USER
-- -----------------------------------------------------

USE `Project`;
DROP procedure IF EXISTS `Project`.`CREATE_USER`;

DELIMITER $$
USE `Project`$$
CREATE PROCEDURE CREATE_USER
(IN usr CHAR(20), pass CHAR(20), fname CHAR(20), lname CHAR(20), email CHAR(20), phone CHAR(20), locationid INT, failedlogin INT)
BEGIN
	INSERT INTO USER(Username, Password, FirstName, LastName, Email, Phone, LocationId, FailedLoginAttempts) VALUES(usr, pass, fname, lname, email, phone, locationid, failedlogin);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure UPDATE_USER
-- -----------------------------------------------------

USE `Project`;
DROP procedure IF EXISTS `Project`.`UPDATE_USER`;

DELIMITER $$
USE `Project`$$
CREATE PROCEDURE UPDATE_USER
(IN usr CHAR(20), pass CHAR(20), fname CHAR(20), lname CHAR(20), email CHAR(20), phone CHAR(20), locationid INT)
BEGIN
	UPDATE USER SET Password = pass, FirstName = fname, LastName = lname, Email = email, Phone = phone, LocationId = locationid WHERE Username = usr;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure CREATE_LOCATION
-- -----------------------------------------------------

USE `Project`;
DROP procedure IF EXISTS `Project`.`CREATE_LOCATION`;

DELIMITER $$
USE `Project`$$
CREATE PROCEDURE CREATE_LOCATION
(IN lat FLOAT, lon FLOAT, streetnum INT, street CHAR(20), city CHAR(20), zip int)
BEGIN
	IF (SELECT COUNT(*) FROM LOCATION WHERE Lattitude = lat AND Longitude = lon) < 1 
		THEN INSERT INTO LOCATION(Lattitude, Longitude, StreetNum, Street, City, Zipcode) VALUES(lat, lon, streetnum, street, city, zip);
	END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure CREATE_DONATION
-- -----------------------------------------------------

USE `Project`;
DROP procedure IF EXISTS `Project`.`CREATE_DONATION`;

DELIMITER $$
USE `Project`$$
CREATE PROCEDURE CREATE_DONATION
(IN productid INT, amount INT, userid INT)
BEGIN
	INSERT INTO DONATION(ProductId, Amount, UserId) VALUES(productid, amount, userid);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure CREATE_PRODUCT
-- -----------------------------------------------------

USE `Project`;
DROP procedure IF EXISTS `Project`.`CREATE_PRODUCT`;

DELIMITER $$
USE `Project`$$
CREATE PROCEDURE CREATE_PRODUCT 
(IN _Type VARCHAR(45), OUT output int)
BEGIN
	insert into Product (Type) values (_Type);
	insert into StoredProduct (Quantity, ProductId) select 0, ProductId from Product where Type = _Type;
	select ProductId into output from Product where Type = _Type;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure GET_DISTANCES
-- -----------------------------------------------------

USE `Project`;
DROP procedure IF EXISTS `Project`.`GET_DISTANCES`;

DELIMITER $$
USE `Project`$$
CREATE PROCEDURE `GET_DISTANCES` 
(IN inLat long, inLon long)
BEGIN
SELECT RequestId,r.DisasterEventID,r.UserID,r.ProductID,QuantityRequested,
					QuantityFulfilled,Expired,NeededByDate,l.lattitude,l.longitude, 
					r.LocationID,streetnum,street,city,p.type as productName,
                    d.type as disasterName,l.zipcode as zipName, 
					69 * DEGREES(ACOS(COS(RADIANS(userLat))
									 * COS(RADIANS(l.lattitude))
					                 * COS(RADIANS(userLon) - RADIANS(l.longitude))
					                 + SIN(RADIANS(userLat))
					                 * SIN(RADIANS(l.lattitude)))) AS distance 
					FROM Request r JOIN Product p on r.ProductId = p.ProductId 
					JOIN disasterevent d on r.DisasterEventId = d.DisasterEventId 
					JOIN location l on r.locationid = l.locationid
                    JOIN ( SELECT  inLat AS userLat, inLon AS userLon) AS p ON 1=1
                    HAVING expired = 0 AND quantityfulfilled < quantityrequested
					ORDER BY distance;
END$$

DELIMITER ;
USE `Project`;

DELIMITER $$

USE `Project`$$
DROP TRIGGER IF EXISTS `Project`.`Product_AFTER_INSERT` $$
USE `Project`$$
CREATE DEFINER = CURRENT_USER TRIGGER `Project`.`Product_AFTER_INSERT` AFTER INSERT ON `Product` FOR EACH ROW
BEGIN
	insert into storedproduct (productid, quantity) values (new.productid,0);
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
