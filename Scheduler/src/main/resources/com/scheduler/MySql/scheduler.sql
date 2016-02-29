-- Table for a `Account Requests`
--CREATE TABLE `classroom`.`accrequests` (
--  `accRequest_id` INT(11) NOT NULL AUTO_INCREMENT,
--  `fName` VARCHAR(45) NOT NULL,
--  `lName` VARCHAR(45) NOT NULL,
--  `email` VARCHAR(45) NOT NULL,
--  `username` VARCHAR(45) NOT NULL,
--  `pass` VARCHAR(45) NOT NULL,
--  `reasoning` VARCHAR(500) NULL,
--  PRIMARY KEY (`accRequest_id`),
--  UNIQUE INDEX `username_UNIQUE` (`username` ASC));
--  UNIQUE INDEX `accRequest_id_UNIQUE` (`username` ASC)); 
  
  
  -- Table for a `User`
  CREATE TABLE `classroom`.`users` (
  `userID` INT(11) NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(45) NULL ,
  `userPassword` VARCHAR(45) NULL,
  `userFirst` VARCHAR(45) NULL,
  `userLast` VARCHAR(45) NULL,
  `userEmail` VARCHAR(45) NULL,
  `userAdmin` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`userID`),
  UNIQUE INDEX `userName_UNIQUE` (`userName` ASC),
  UNIQUE INDEX `userID_UNIQUE` (`userID` ASC),
  UNIQUE INDEX `userEmail_UNIQUE` (`userEmail` ASC));


      -- Table for a `classes`  
  CREATE TABLE `classroom`.`classes` (
  `classID` INT NOT NULL AUTO_INCREMENT,
  `classNumber` INT NOT NULL,
  `classSubject` VARCHAR(8) NULL,
  `classCatalog` VARCHAR(7) NULL,
  `classSection` VARCHAR(5) NULL,
  `classCombination` VARCHAR(5) NOT NULL DEFAULT 'NA',
  `className` VARCHAR(45) NOT NULL,
  `classDescription` VARCHAR(70) NULL,
  `classAcadGroup` VARCHAR(7) NULL,
  `classCapacity` INT NOT NULL DEFAULT 0,
  `classEnrolled` INT NOT NULL DEFAULT 0,
  `classDays` VARCHAR(5) NOT NULL,
  `classTimeStart` VARCHAR(45) NOT NULL,
  `classTimeEnd` VARCHAR(45) NOT NULL,
  `classDateStart` VARCHAR(45) NOT NULL,
  `classDateEnd` VARCHAR(45) NOT NULL,
  `classInstructFirst` VARCHAR(45) NOT NULL,
  `classInstructLast` VARCHAR(45) NOT NULL,
  `classRoom` VARCHAR(45) NULL,
  `classCampus` VARCHAR(10) NULL,
  `classMode` VARCHAR(3) NULL,
  `classComponent` VARCHAR(7) NULL,
  `classSession` VARCHAR(7) NULL,
  `classCrsAttrVal` VARCHAR(7) NULL,
  `classMon` INT NOT NULL DEFAULT 0,
  `classTues` INT NOT NULL DEFAULT 0,
  `classWed` INT NOT NULL DEFAULT 0,
  `classThurs` INT NOT NULL DEFAULT 0,
  `classFri` INT NOT NULL DEFAULT 0,
  `classSat` INT NOT NULL DEFAULT 0,
  `classSun` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`classID`),
  UNIQUE INDEX `classID_UNIQUE` (`classID` ASC));
 -- Some classes have the same ClassNbr, had to remove below restriction
  --UNIQUE INDEX `classNbr_UNIQUE` (`classNbr` ASC));
  
  -- Table for a `classrooms`  
  CREATE TABLE `classroom`.`classrooms` (
  `roomID` INT NOT NULL AUTO_INCREMENT,
  `roomCapacity` INT NULL,
  `roomName` VARCHAR(45) NULL,
  `roomDeskType` VARCHAR(45) NULL DEFAULT 'Any' ,
  `roomChairType` VARCHAR(45) NULL DEFAULT 'Any' ,
  `roomBoardType` VARCHAR(45) NULL DEFAULT 'Any' ,
  `roomDistLearning` VARCHAR(45) NULL,
  `roomType` VARCHAR(45) NULL  DEFAULT 'Any' ,
  `roomProjectors` INT NULL DEFAULT 0,
  PRIMARY KEY (`roomID`),
  UNIQUE INDEX `roomID_UNIQUE` (`roomID` ASC));
  UNIQUE INDEX `roomName_UNIQUE` (`roomID` ASC));
  
    -- Table for a `Instructors` 
  CREATE TABLE `classroom`.`instructors` (
  `instructID` INT NOT NULL,
  `instructFirst` VARCHAR(45) NULL,
  `instructLast` VARCHAR(45) NULL,
  `instructBoard` VARCHAR(45) NULL DEFAULT 'Any',
  `instructDesk` VARCHAR(45) NULL DEFAULT 'Any',
  `instructChair` VARCHAR(45) NULL DEFAULT 'Any',
  `instructComment` VARCHAR(250) NULL,
  PRIMARY KEY (`instructID`),
  UNIQUE INDEX `instrucID_UNIQUE` (`instructID` ASC));
  
      -- Table for a `Conflicts` 
  CREATE TABLE `classroom`.`conflicts` (
  `conflictID` INT NOT NULL,
  `conflictClass1` VARCHAR(45) NULL,
  `conflictClass2` VARCHAR(45) NULL,
  `conflictType` VARCHAR(45) NULL,
  `conflictValue1` VARCHAR(45) NULL,
  `conflictValue2` VARCHAR(45) NULL,
  UNIQUE INDEX `conflictID_UNIQUE` (`conflictID` ASC),
  PRIMARY KEY (`conflictID`));

