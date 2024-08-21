-- Groups 테이블에 초기 데이터 삽입
INSERT INTO Groups (group_id, group_name, created_date, modified_date)
VALUES (1, 'Admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO Groups (group_id, group_name, created_date, modified_date)
VALUES (2, 'Users', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Users 테이블에 초기 데이터 삽입
INSERT INTO Users (user_id, group_id, id, password, name, phone_number, is_delete, modified_date, signup_date)
VALUES (1, 1, 'admin', 'admin123', 'Admin Users', '010-1234-5678', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

--INSERT INTO Users (user_id, group_id, id, password, name, phone_number, is_delete, modified_date, signup_date)
--VALUES (2, 2, 'user1', 'user123', 'John Doe', '010-9876-5432', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- board 테이블 초기 데이터 삽입
INSERT INTO board
(board_title, board_description, created_date)
VALUES('게시판 이름', '게시판 설명', CURRENT_TIMESTAMP);

INSERT INTO post (user_id, group_id, board_id, title, content, is_delete, created_date, modified_date)
VALUES (1, 1, 1, 'First Post Title', 'This is the content of the first post.', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO Comment (comment_user_id, comment_post_id, content, is_delete, modified_date, created_date)
VALUES (1, 1, 'This is the first comment.', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO Comment (comment_user_id, comment_post_id, content, is_delete, modified_date, created_date)
VALUES (1, 1, 'This is the second comment.', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO Comment (comment_user_id, comment_post_id, content, is_delete, modified_date, created_date)
VALUES (1, 1, 'This is the third comment.', 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);