package com.coma.coma.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Integer postId;
    private Long boardId;
    private Integer userId; // 유저 아이디
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdDate; // 추가적인 정보 (예: 생성일)
    private LocalDateTime modifiedDate; // 추가적인 정보 (예: 수정일)
}
