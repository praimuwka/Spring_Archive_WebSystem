CREATE
DATABASE `course_spring` ;
CREATE TABLE `course_spring`.`users`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `login`      varchar(255) NOT NULL,
    `first_name` varchar(50)  NOT NULL,
    `last_name`  varchar(100) NOT NULL,
    `password`   varchar(255) NOT NULL,
    `role`       varchar(50)  NOT NULL DEFAULT 'USER',
    `status`     varchar(20)           DEFAULT 'ACTIVE',
    PRIMARY KEY (`id`),
    UNIQUE KEY `users_login_uindex` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=cp1251;
CREATE TABLE `course_spring`.`department`
(
    `boss`          varchar(255) NOT NULL,
    `id_department` int          NOT NULL AUTO_INCREMENT,
    `phone`         varchar(12)  NOT NULL,
    `name`          varchar(45)  NOT NULL,
    PRIMARY KEY (`id_department`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=cp1251;
CREATE TABLE `course_spring`.`organisation`
(
    `name`            varchar(255) NOT NULL,
    `phone`           varchar(12)  DEFAULT NULL,
    `fax`             int          DEFAULT NULL,
    `email`           varchar(255) DEFAULT NULL,
    `post_index`      int          NOT NULL,
    `id_organisation` int          NOT NULL AUTO_INCREMENT,
    `city`            varchar(255) NOT NULL,
    `address`         varchar(255) NOT NULL,
    PRIMARY KEY (`id_organisation`),
    UNIQUE KEY `phone` (`phone`),
    UNIQUE KEY `fax` (`fax`),
    UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=cp1251;
CREATE TABLE `course_spring`.`document_type`
(
    `id_doctype` int         NOT NULL AUTO_INCREMENT,
    `name`       varchar(30) NOT NULL,
    PRIMARY KEY (`id_doctype`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=cp1251;
CREATE TABLE `course_spring`.`document`
(
    `id_document`  int NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) DEFAULT NULL,
    `type`         int NOT NULL,
    `author`       varchar(255) DEFAULT NULL,
    `organisation` int          DEFAULT NULL,
    `year_created` int          DEFAULT NULL,
    `pages_number` int          DEFAULT NULL,
    PRIMARY KEY (`id_document`),
    KEY            `document_fk0` (`type`),
    KEY            `document_fk1` (`organisation`),
    CONSTRAINT `document_fk0` FOREIGN KEY (`type`) REFERENCES `document_type` (`id_doctype`),
    CONSTRAINT `document_fk1` FOREIGN KEY (`organisation`) REFERENCES `organisation` (`id_organisation`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=cp1251;
CREATE TABLE `course_spring`.`issuance_registry`
(
    `id_record`         int          NOT NULL AUTO_INCREMENT,
    `department`        int      DEFAULT NULL,
    `datetime_given`    datetime     NOT NULL,
    `datetime_returned` datetime DEFAULT NULL,
    `document`          int          NOT NULL,
    `employee`          varchar(255) NOT NULL,
    PRIMARY KEY (`id_record`),
    KEY                 `issuance_registry_fk1` (`document`),
    KEY                 `employee_idx` (`employee`) /*!80000 INVISIBLE */,
    KEY                 `department_idx` (`department`),
    CONSTRAINT `issuance_registry_fk0` FOREIGN KEY (`department`) REFERENCES `department` (`id_department`),
    CONSTRAINT `issuance_registry_fk1` FOREIGN KEY (`document`) REFERENCES `document` (`id_document`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=cp1251;
DELIMITER
$$
CREATE
DEFINER=`root`@`localhost` PROCEDURE `course_spring`.`GET_ISSUED`()
BEGIN
Select registry.datetime_given as dt,
       doc.name                as docName,
       registry.employee       as employee,
       dep.name                as depName,
       registry.document       as docId
from (select department, datetime_given, document, employee
      from issuance_registry
      where datetime_returned is null) as registry
         left join department as dep on dep.id_department = registry.department
         left join document as doc on doc.id_document = registry.document;
END$$
DELIMITER ;
DELIMITER
$$
CREATE
DEFINER=`root`@`localhost` PROCEDURE `course_spring`.`GET_JOURNAL`()
BEGIN
Select registry.dt,
       registry.action,
       doc.name          as docName,
       registry.employee as employee,
       dep.name          as depName,
       registry.document as docId
from (select department, "Выдача" as 'action', datetime_given as dt, document, employee
      from issuance_registry
      UNION
      select department, "Возврат" as 'action', datetime_returned as dt, document, employee
      from issuance_registry
      where datetime_returned is not null) as registry
         left join department as dep on dep.id_department = registry.department
         left join document as doc on doc.id_document = registry.document
order by dt desc;
END$$
DELIMITER ;



