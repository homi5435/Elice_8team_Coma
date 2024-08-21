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

