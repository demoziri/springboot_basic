# DB 생성
DROP DATABASE IF EXISTS sbs_proj_2023;
CREATE DATABASE sbs_proj_2023;
USE sbs_proj_2023;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물, 테스트 데이터 생성
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

#게시물 테이블에 회원정보 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

# 기존 게시물의 작성자를 2번으로 지정
UPDATE article
SET memberId = 2;
WHERE memberid = 0;

SELECT * FROM article;

# 회원 테이블 생성
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
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부',
    delDate DATETIME COMMENT '탈퇴날짜'
);

# 회원, 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authLevel = 7,
`name` = '관리자',
nickname = '관리자',
cellphoneNo = '01011111111',
email = 'admin@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '사용자1',
nickname = '사용자1',
cellphoneNo = '01011111111',
email = 'user1@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = '사용자2',
nickname = '사용자2',
cellphoneNo = '01011111111',
email = 'user2@gmail.com';

SELECT * FROM `member`;

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free1(자유게시판1), free2(자유게시판2,...',
    `name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부(0=탈퇴전, 1=탈퇴)',
    delDate DATETIME COMMENT '삭제날짜'
);

# 기본 게시판 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유';


SELECT * FROM article;

# 게시판 테이블에 boardId 컬럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER `memberId`;

# 1, 2번 게시물을 공지사항 게시물로 지정
UPDATE article
SET boardId = 1
WHERE id IN(1,2);

# 3번 게시물을 자유게시판 게시물로 지정
UPDATE article
SET boardId = 2
WHERE id IN(3);

SELECT * FROM board WHERE id = 1;
SELECT * FROM board WHERE id = 2;

/*
# 게시물 개수 늘리기
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
select NOW(), now(), FLOOR(RAND() *2) + 1, FLOOR(RAND() *2) + 1, concat('제목_', rand()), CONCAT('내용_', RAND())
from article;
*/

SELECT * FROM article;

DESC article;

ALTER TABLE article
ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;

# like 테이블 생성
CREATE TABLE reactionPoint (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(30) NOT NULL COMMENT '관련데이터타입코드',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
    `point` SMALLINT(2) NOT NULL
);

# 리액션포인트 테스트 데이터
# 1번 회원이 1번 article에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
reltypeCode = 'article',
relId = 1,
`point` = -1;

# 1번 회원이 2번 article에 대해서 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
reltypeCode = 'article',
relId = 2,
`point` = 1;

# 2번 회원이 1번 article에 대해서 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
reltypeCode = 'article',
relId = 1,
`point` = -1;

# 2번 회원이 2번 article에 대해서 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
reltypeCode = 'article',
relId = 2,
`point` = 1;

# 3번 회원이 1번 article에 대해서 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
reltypeCode = 'article',
relId = 1,
`point` = 1;

SELECT * FROM reactionPoint;

#게시물 테이블 goodReactionPoint 컬럼추가
ALTER TABLE article
ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;


#게시물 테이블 badReactionPoint 컬럼추가
ALTER TABLE article
ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

/*select RP.relTypeCode, RP.relId,
SUM(IF(RP.point > 0,RP.point,0)) as goodReactionPoint,
SUM(IF(RP.point < 0,RP.point * -1 ,0)) AS badReactionPoint
from reactionPoint AS RP
where relTypeCode = 'article'
GROUP BY RP.relTypeCode, RP.relId*/

UPDATE article AS A
INNER JOIN (
    SELECT RP.relId,
    SUM(IF(RP.point > 0,RP.point,0)) AS goodReactionPoint,
    SUM(IF(RP.point < 0,RP.point * -1 ,0)) AS badReactionPoint
    FROM reactionPoint AS RP
    WHERE relTypeCode = 'article'
    GROUP BY RP.relTypeCode, RP.relId
)AS RP_SUM
ON A.id = RP_SUM.relId
SET A.goodReactionPoint = RP_SUM.goodReactionPoint,
A.badReactionPoint = RP_SUM.badReactionPoint;

# 댓글 테이블 생성
CREATE TABLE reply(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    reltypeCode CHAR(30) NOT NULL COMMENT '관련데이터타입코드',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련데이터번호',
    `body` TEXT NOT NULL
)

# 댓글 테스트 데이터
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글1';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글2';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`body` = '댓글3';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글234';

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`body` = '댓글123';

SELECT * FROM reply;


#댓글에 좋아요 수, 싫어요 수 컬럼 추가
ALTER TABLE reply
ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

ALTER TABLE reply
ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;


/*alter table `reply` add index(`relTypeCode`,`relId`);*/

EXPLAIN SELECT R.*,
M.nickname AS extra_writerName
FROM reply AS R
LEFT JOIN `member` AS M
ON r.memberId = M.id
WHERE R.relTypeCode='article'
AND R.relId=1
ORDER BY R.id DESC;
