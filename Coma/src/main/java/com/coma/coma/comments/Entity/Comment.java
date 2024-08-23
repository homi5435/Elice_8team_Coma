package com.coma.coma.comments.Entity;


import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer commentId;       // 댓글 ID (자동 증가)
    private Integer userId;   // 사용자 ID
    private Integer groupId; // group ID
    private Integer postId;   // 포스트 ID
    private String content;       // 댓글 내용
    private String isDelete;      // 삭제 여부
    private LocalDateTime modifiedDate; // 수정 날짜
    private LocalDateTime createdDate = LocalDateTime.now();   // 생성 날짜

    public void updateModifiedDate(){ this.modifiedDate = LocalDateTime.now(); }
}
