package com.coma.coma.board.dto;


import com.coma.coma.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateRequest {

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String boardTitle;

    @NotBlank(message = "설명은 공백일 수 없습니다.")
    private String boardDescription;

    @NotNull(message = "유저 id가 없습니다.")
    private Long userId;

    public Board toEntity() {
        Board board = new Board(userId, boardTitle, boardDescription);
        return board;
    }
}
