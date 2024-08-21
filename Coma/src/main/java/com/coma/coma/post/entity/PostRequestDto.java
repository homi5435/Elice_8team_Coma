package com.coma.coma.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    private Long postId;
    private Long userId;
    private Long groupId;
    private Long boardId;
    private String title;
    private String content;
    private String isDelete;

}
