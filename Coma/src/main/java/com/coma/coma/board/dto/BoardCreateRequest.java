package com.coma.coma.board.dto;


import com.coma.coma.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardCreateRequest {

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String board_title;

    @NotBlank(message = "설명은 공백일 수 없습니다.")
    private String board_description;

    @NotNull(message = "유저 id가 없습니다.")
    private Long userId;

    public Board toEntity() {
        Board board = new Board(userId, board_title, board_description);
        return board;
    }
}
