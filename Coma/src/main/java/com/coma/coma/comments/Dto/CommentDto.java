package com.coma.coma.comments.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer commentId;
    //TODO UserId 받아와서 설정
    private Integer userId;
    private Integer groupId;
    private Integer postId;
    private String content;
    private String isDelete = "N";
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate = LocalDateTime.now();
    public void updateModifiedDate(){ this.modifiedDate = LocalDateTime.now(); }
}