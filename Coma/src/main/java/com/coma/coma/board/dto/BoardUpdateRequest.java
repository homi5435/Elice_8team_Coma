package com.coma.coma.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateRequest {

    @NotBlank
    private String board_title;

    @NotBlank
    private String board_description;
}
