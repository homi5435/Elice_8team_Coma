-- Groups 테이블에 초기 데이터 삽입
INSERT INTO Groups (group_id, group_name, created_date, modified_date)
VALUES (1, 'Admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO Groups (group_id, group_name, created_date, modified_date)
VALUES (2, 'Users', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Users 테이블에 초기 데이터 삽입
INSERT INTO Users (group_id, id, password, name, phone_number, is_delete, modified_date, signup_date)
VALUES (1, 'admin', '$2a$10$s4Tj0wgK19PZY07r989kLewVCvdjtA6.UTIVZE7iJtn/5PRJ0m4ai', 'Admin Users', '01012345678', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO Users (group_id, id, password, name, phone_number, is_delete, modified_date, signup_date)
VALUES (2, 'user1', '$2a$10$2WRUXjkW.Zwpp4p2BNkrpeQUqkroGsGfbFeX0amuEtxHnm8LnQMxK', '유저1', '01098765432', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- board 테이블 초기 데이터 삽입
INSERT INTO board
(board_title, board_description, created_date, user_id)
VALUES('게시판 이름', '게시판 설명', CURRENT_TIMESTAMP, 1);

INSERT INTO post (user_id, group_id, board_id, title, content, is_delete, created_date)
VALUES (1, 1, 1, 'First Post Title', 'This is the content of the first post.', 'N', CURRENT_TIMESTAMP);
