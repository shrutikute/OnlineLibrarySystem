CREATE USER 'library_user'@'localhost' IDENTIFIED BY 'library';

GRANT ALL PRIVILEGES ON cs532_online_library.* TO 'library_user'@'localhost' WITH GRANT OPTION;

SHOW GRANTS FOR 'library_user'@'localhost';

delimiter ;
DROP SCHEMA IF EXISTS `cs532_online_library`; 
CREATE SCHEMA `cs532_online_library` ;
use `cs532_online_library`;

delimiter $$

CREATE TABLE `member` (
  `memid` int(15) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  `address` varchar(15) NOT NULL,
  `classification` varchar(15) NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
   PRIMARY KEY (`memid`),
   UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `book` (
  `bookid` int(15) NOT NULL AUTO_INCREMENT,
  `title` varchar(15) NOT NULL,
  `author` varchar(15) NOT NULL,
  `isbn` int(10) NOT NULL,
  PRIMARY KEY (`bookid`),
  UNIQUE KEY `name_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `bookissue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memid` int(15) NOT NULL,
  `bookid` int(15) NOT NULL,
  `issuedate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`memid`) references member(memid) ON DELETE CASCADE,
  FOREIGN KEY (`bookid`) references book(bookid) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1$$

delimiter ;

INSERT INTO `cs532_online_library`.`book` (`title`, `author`, `isbn`) VALUES ('MATH', 'PAUL', '1111');
INSERT INTO `cs532_online_library`.`book` (`title`, `author`, `isbn`) VALUES ('HISTORY', 'KATHLENE', '2222');
INSERT INTO `cs532_online_library`.`book` (`title`, `author`, `isbn`) VALUES ('SCIENCE', 'FAITH', '3333');
INSERT INTO `cs532_online_library`.`book` (`title`, `author`, `isbn`) VALUES ('ECONOMY', 'ALISON', '4444');
