package com.coma.coma.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public class BoardUpdateRequest {

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String boardTitle;

    @NotBlank(message = "설명은 공백일 수 없습니다.")
    private String boardDescription;
}
