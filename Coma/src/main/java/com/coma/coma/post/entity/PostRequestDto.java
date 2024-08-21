package com.coma.coma.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    // 유저 정보
    private int userId;
    private int groupId;

    // 게시판 정보
    private Long boardId;

    // User로부터 입력받은 게시글 정보
    private String title;
    private String content;

}
