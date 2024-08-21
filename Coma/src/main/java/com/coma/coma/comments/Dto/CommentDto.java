package com.coma.coma.comments.Dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int commentId;
    private int commentUserId;
    private int commentPostId;
    private String content;
    private String isDelete;
    private Timestamp modifiedDate;
    private Timestamp createdDate;
}