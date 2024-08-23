
-- Groups 테이블 생성
CREATE TABLE Groups (
                        group_id INT PRIMARY KEY,
                        group_name VARCHAR(50) NOT NULL,
                        created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Users 테이블 생성
CREATE TABLE Users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       group_id INT,
                       id VARCHAR(20),
                       password VARCHAR(80),
                       name VARCHAR(20),
                       phone_number VARCHAR(20),
                       is_delete CHAR(1) DEFAULT 'N',
                       modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       signup_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS board;

CREATE TABLE board (
                       board_id BIGINT NOT NULL AUTO_INCREMENT,
                       board_title VARCHAR(255) NOT NULL,
                       board_description VARCHAR(255) NOT NULL,
                       is_delete CHAR(1) NOT NULL DEFAULT 'N',
                       created_date TIMESTAMP NULL DEFAULT NULL,
                       modified_date TIMESTAMP NULL DEFAULT NULL,
                       PRIMARY KEY (board_id)
);



-- Create the post table
CREATE TABLE post (
                      post_id INT AUTO_INCREMENT PRIMARY KEY,
                      user_id INT NOT NULL,
                      group_id INT NOT NULL,
                      board_id BIGINT NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      content TEXT NOT NULL,
                      is_delete CHAR(1) DEFAULT 'N' NOT NULL,
                      created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                      modified_date TIMESTAMP NULL,
                      CONSTRAINT FK_User FOREIGN KEY (user_id) REFERENCES users(user_id),
                      CONSTRAINT FK_Group FOREIGN KEY (group_id) REFERENCES groups(group_id),
                      CONSTRAINT FK_Board FOREIGN KEY (board_id) REFERENCES board(board_id)
);

CREATE TABLE comment (
                    comment_id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT NOT NULL,
                    post_id INT NOT NULL,
                    content TEXT NOT NULL,
                    is_delete CHAR(1) DEFAULT 'N' NOT NULL,
                    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    CONSTRAINT FK_Com_User FOREIGN KEY (user_id) REFERENCES users(user_id),
                    CONSTRAINT FK_Com_Post FOREIGN KEY (post_id) REFERENCES post(post_id)
);

