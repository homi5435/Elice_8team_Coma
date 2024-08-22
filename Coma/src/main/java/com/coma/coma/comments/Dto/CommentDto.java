package com.coma.coma.comments.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int commentId;
    //TODO UserId 받아와서 설정
    private int UserId = 1;
    private int PostId;
    private String content;
    private String isDelete = "N";
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate = LocalDateTime.now();
    public void updateModifiedDate(){ this.modifiedDate = LocalDateTime.now(); }
}