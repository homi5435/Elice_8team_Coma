package com.coma.coma.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    private Long postId;
    private Long userId;
    private Long groupId;
    private Long boardId;
    private String title;
    private String content;
    private String isDelete = "N";      // default value is "N"

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate;

    public void updateModifiedDate() {
        this.modifiedDate = LocalDateTime.now();
    }

}
