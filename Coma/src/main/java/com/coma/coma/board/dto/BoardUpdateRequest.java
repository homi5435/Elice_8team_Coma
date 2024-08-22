package com.coma.coma.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateRequest {

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String board_title;

    @NotBlank(message = "설명은 공백일 수 없습니다.")
    private String board_description;
}
