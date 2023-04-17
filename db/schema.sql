DROP DATABASE IF EXISTS springboot_basic;
CREATE DATABASE springboot_basic;
USE springboot_basic;

CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

DROP TABLE article

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';


INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';


INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

SELECT * FROM article;


CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '(3=일반, 7=관리자)',
    loginPw CHAR(60) NOT NULL,
    `name` CHAR(20) NOT NULL,
    `nickname` CHAR(20) NOT NULL,
    cellphoneNo CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delSta`member`tus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '(탈퇴여부)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authLevel = 7,
`name` = '관리자',
nickname = '관리자',
cellphoneNo = '01012341234',
email = 'admin@sbs.com';

#member
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'member',
loginPw = 'member',
authLevel = 3,
`name` = '회원1',
nickname = '회원1',
cellphoneNo = '01012341234',
email = 'admin@sbs.com';


INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'member2',
loginPw = 'member2',
authLevel = 3,
`name` = '회원2',
nickname = '회원2',
cellphoneNo = '01012341234',
email = 'admin@sbs.com';



SELECT * FROM MEMBER;

