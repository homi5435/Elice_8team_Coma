package com.coma.coma.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    // User로부터 입력받은 게시글 정보
    private String title;
    private String content;
    private String imageUrl;

}
