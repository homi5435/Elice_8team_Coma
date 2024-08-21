package com.coma.coma.board.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardPostDto {

    private Long board_idx;

    private String board_title;

    private String board_description;

    private String is_delete;

    private String reg_date;

    private String mod_date;
}
