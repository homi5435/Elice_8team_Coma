# COMA : 개발자를 위한 커뮤니티 공간

## 프로젝트 소개
COMA는 개발자를 위한 커뮤니티 플랫폼으로, 사용자들이 다양한 주제에 대해 게시글을 작성하고 댓글을 통해 소통할 수 있는 공간입니다. 이 프로젝트는 개발자들이 정보를 공유하고 협업할 수 있는 환경을 제공하는 것을 목표로 합니다.

## 팀원 소개
| 이름   | 역할    | 담당 기능                        |
|--------|---------|----------------------------------|
| 이서율 | User    | 사용자 (회원가입, 로그인 등) |
| 윤지현 | Post    | 게시글 (작성, 수정, 삭제, 이미지 업로드 등) |
| 유호성 | Comment | 댓글 (작성, 수정, 삭제 등)   |
| 김준수 | Board   | 게시판 (카테고리 생성, 수정, 삭제 등)   |

## ERD
- [ERD 링크](https://www.erdcloud.com/d/qfq5QyeHS89xjxsq9)

## 기술 스택
- **Frontend**: Bootstrap, Thymeleaf
- **Backend**: Spring Boot
- **Database**: MySQL
- **Security**: Spring Security, JWT(JSON Web Token)
- **DevOps**: AWS (EC2, S3, RDS)

## 주요 기능
- **사용자**: 회원가입, 로그인, 로그아웃, 휴대폰 인증
- **게시글**: 작성, 수정, 삭제, 이미지 업로드 기능
- **댓글**: 작성, 수정, 삭제, 페이지네이션
- **게시판**: 생성, 수정, 삭제

## 배포 환경
```
- 서버 : AWS EC2, GCP VM
    - OS: Ubuntu 24.04 LTS(EC2) / Ubuntu 20.04.6 LTS(GCP)
    - JRE: OpenJDK 22
    - 애플리케이션 서버: Spring Boot (내장 Tomcat 사용)
- 데이터베이스: AWS RDS (MySQL)
    - MySQL 버전: 8.0.35
- 파일 저장소: AWS S3
- 기타 라이브러리 및 도구
    - AWS CLI: AWS 명령줄 인터페이스 v2
```

## 개발 환경
```
- 운영체제: Windows 11, macOS Sonoma
- IDE: IntelliJ IDEA
- 빌드 도구: Gradle
- JDK 버전: JDK 22
- 버전 관리: Git / Gitlab
- 기타 툴: Postman (API 테스트), Lombok
```

## 의존성 목록
### 주요 라이브러리 및 버전
- **Spring Boot**:
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-jdbc`
  - `spring-boot-starter-validation:3.2.0`
  - `spring-boot-starter-security`
  - `spring-boot-starter-thymeleaf`
  - `spring-boot-starter-web`
- **JPA 및 데이터베이스**:
  - `mysql:mysql-connector-j`
- **보안 및 인증**:
  - `io.jsonwebtoken:jjwt-api:0.11.5`
  - `io.jsonwebtoken:jjwt-impl:0.11.5`
  - `io.jsonwebtoken:jjwt-jackson:0.11.5`
- **Thymeleaf**:
  - `org.thymeleaf.extras:thymeleaf-extras-springsecurity6`
- **Mapper**:
  - `org.mapstruct:mapstruct:1.5.5.Final`
  - `org.mapstruct:mapstruct-processor:1.5.5.Final`
- **Lombok**:
  - `org.projectlombok:lombok`
- **AWS SDK**:
  - `software.amazon.awssdk:s3:2.27.13`
  - `com.amazonaws:aws-java-sdk-s3:1.12.770`
- **기타**:
  - `net.nurigo:sdk:4.3.0` (SMS API)
- **테스트**:
  - `spring-boot-starter-test`
  - `spring-security-test`
  - `junit-platform-launcher`
- **DevTools**:
  - `spring-boot-devtools`

## API 명세서
### 접속 관리
- 엔드포인트

| **MVP** | **Method** | **URI**                         | **Description**                       | **Request Body**                        | **Request Params** | **Response**                                           |
|---------|------------|---------------------------------|---------------------------------------|-----------------------------------------|--------------------|--------------------------------------------------------|
| user    | GET        | /api/users/{id}                  | 사용자 정보 조회                     |                                         | id                 | 200 OK: 사용자 정보 반환 (JSON) <br> 404 Not Found: 사용자를 찾을 수 없음 |
| user    | POST       | /api/users/register              | 회원가입                             | UserDto (JSON)                          |                    | 201 Created: 사용자 등록 성공 <br> 400 Bad Request: 잘못된 요청 데이터 |
| user    | POST       | /api/users/login                 | 로그인                               | LoginRequestDto (JSON)                  |                    | 200 OK: 인증 성공, JWT 토큰 반환 <br> 401 Unauthorized: 인증 실패 |
| user    | POST       | /api/users/logout                | 로그아웃                              |                                         |                    | 200 OK: 로그아웃 성공                                |
| user    | GET        | /api/users/check-id/{id}         | 아이디 중복확인                       |                                         | id                 | 200 OK: 중복 여부 (true or false)                      |
| user    | POST       | /api/sms/send                    | 전화번호 인증                        |                                         |                    |                                                        |
| user    | POST       | /api/sms/verify                  | 전화번호 검증                        |                                         |                    |                                                        |
| user    | POST       | /api/users/reset-password        | 사용자 비밀번호 재설정                | id, phoneNumber, newPassword (JSON)      |                    | 200 OK: 비밀번호 재설정 성공 <br> 400 Bad Request: 잘못된 요청 데이터 |

- 웹페이지

| **MVP** | **Method** | **URI**                         | **Description**                       | **Response**                              |
|---------|------------|---------------------------------|---------------------------------------|-------------------------------------------|
| user    | GET        | /users/login                     | 로그인 페이지를 반환                | user/login.html                           |
| user    | GET        | /users/register                  | 회원가입 페이지를 반환              | user/register.html                        |
| user    | GET        | /users/{id}                      | 사용자 정보를 조회 후 페이지를 반환 | 사용자 정보 페이지 (user/userInfo.html) <br> 인증된 사용자만 접근 가능. 다른 사용자가 접근 시 error/access-denied 페이지로 리다이렉트 |
| user    | GET        | /users/edit/{id}                 | 사용자 정보를 수정할 수 있는 페이지를 반환 | user/editUserInfo.html                   |
| user    | POST       | /users/update/{id}               | 회원정보 업데이트                    | id, name, phoneNumber, password <br> 업데이트 성공 시 해당 사용자 페이지로 리다이렉트 <br> JWT 토큰 갱신 |
| user    | POST       | /users/delete/{id}               | 회원정보 삭제                        | 사용자 삭제 후 boards 페이지로 리다이렉트 <br> JWT 토큰 삭제 |
| user    | GET        | /users/login/findPassword        | 비밀번호 찾기 페이지를 반환          | user/findPassword.html                   |

### 게시글
- 엔드포인트

| **MVP** | **Method** | **URI**                | **Description**        | **Request Params**                        | **Response**                          |
|---------|------------|------------------------|------------------------|-------------------------------------------|---------------------------------------|
| post    | POST       | /api/posts/{boardId}   | 게시물 생성 요청      | boardId (PathVariable), PostRequestDto (Body) | PostResponseDto (Body), Status 201 Created |
| post    | PUT        | /api/posts/{postId}    | 게시물 수정 요청      | postId (PathVariable), PostRequestDto (Body)  | PostResponseDto (Body), Status 200 OK    |
| post    | DELETE     | /api/posts/{postId}    | 게시물 삭제 요청      | postId (PathVariable)                      | Status 204 No Content                  |

- 웹페이지

| **MVP** | **Method** | **URI**                      | **Description**                  | **Request Params**        | **Response**      |
|---------|------------|------------------------------|----------------------------------|---------------------------|-------------------|
| post    | GET        | /page/posts/new/{boardId}    | 새 게시물 작성 페이지 이동      | boardId (PathVariable)    | createPost.html   |
| post    | GET        | /page/posts/{postId}         | 게시물 상세보기                  | postId (PathVariable)     | post.html         |
| post    | GET        | /page/posts/edit/{postId}    | 게시물 수정 페이지 이동         | postId (PathVariable)     | editPost.html     |
| post    | GET        | /page/posts/list/{boardId}   | 게시물 목록 보기                 | boardId (PathVariable)    | board.html        |

- 이미지 업로드

| **MVP** | **Method** | **URI**          | **Description**             | **Request Params** | **Response** |
|---------|------------|------------------|-----------------------------|--------------------|--------------|
| image   | POST       | /api/images      | 이미지 S3에 업로드          | file (RequestParam) | Status 200 OK |

### 게시판

| **MVP** | **Method** | **URI**                | **Description**   |
|---------|------------|------------------------|-------------------|
| board   | GET        | /api/boards            | 조회              |
| board   | POST       | /api/boards            | 추가              |
| board   | POST       | /api/boards/{boardId}/edit | 수정            |
| board   | DELETE     | /api/boards/{boardId}  | 삭제              |


### 댓글

| **MVP**   | **Method** | **URI**                    | **Description**           |
|-----------|------------|----------------------------|---------------------------|
| comment   | POST       | /comments                  | 댓글 추가                 |
| comment   | GET        | /comments/{postId}          | 댓글 조회(게시글에 따른)   |
| comment   | POST       | /comments/{id}/update       | 댓글 수정                 |
| comment   | POST       | /comments/{id}/delete       | 댓글 삭제                 |
| comment   | POST       | /comments/{id}/edit         | 수정할 댓글 정보 가져오기 |
