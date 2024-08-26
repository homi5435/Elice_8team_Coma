package com.coma.coma.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private Long userId;

    @Column(nullable = false, unique = true)
    private String boardTitle;

    @Column(nullable = false)
    private String boardDescription;

    @Column(nullable = false)
    private String isDelete = "N";

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;


    public Board(Long user_id, String board_title, String board_description) {
        this.userId = user_id;
        this.boardTitle = board_title;
        this.boardDescription = board_description;
    }
}
