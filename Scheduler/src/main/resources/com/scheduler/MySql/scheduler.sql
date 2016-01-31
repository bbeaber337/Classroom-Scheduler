-- Table for a `Account Requests`
CREATE TABLE `classroom`.`accrequests` (
  `accRequest_id` INT(11) NOT NULL AUTO_INCREMENT,
  `fName` VARCHAR(45) NOT NULL,
  `lName` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `pass` VARCHAR(45) NOT NULL,
  `reasoning` VARCHAR(500) NULL,
  PRIMARY KEY (`accRequest_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));
  UNIQUE INDEX `accRequest_id_UNIQUE` (`username` ASC));
  
  
  -- Table for a `User`
  CREATE TABLE `classroom`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL ,
  `pass` VARCHAR(45) NULL,
  `fName` VARCHAR(45) NULL,
  `lName` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `admin` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC));

    -- Table for a `classes`  TEMPORARY for testing (Can be removed)
  CREATE TABLE `classroom`.`classes` (
  `class_id` INT(11) NOT NULL AUTO_INCREMENT,
  `classNbr` INT(10) NOT NULL,
  `subject` VARCHAR(10) NOT NULL,
  `catalog` INT(10) NOT NULL,
  `section` INT(5) NOT NULL,
  `combo` VARCHAR(1) NULL,
  `descr` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`class_id`),
  UNIQUE INDEX `classNbr_UNIQUE` (`classNbr` ASC));