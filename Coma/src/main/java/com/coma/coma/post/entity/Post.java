package com.coma.coma.post.entity;

import com.coma.coma.post.dto.PostRequestDto;
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

    private Integer postId;
    private Integer userId;
    private Integer groupId;
    private Long boardId;
    private String title;
    private String content;
    private String isDelete = "N";      // default value is "N"

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate;

    public void updateModifiedDate() {
        this.modifiedDate = LocalDateTime.now();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

}
