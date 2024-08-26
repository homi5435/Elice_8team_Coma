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
    private Long board_id;

    private Long user_id;

    @Column(nullable = false, unique = true)
    private String board_title;

    @Column(nullable = false)
    private String board_description;

    @Column(nullable = false)
    private String is_delete = "N";

    @Column
    private LocalDateTime created_date;

    @Column
    private LocalDateTime modified_date;


    public Board(Long user_id, String board_title, String board_description) {
        this.user_id = user_id;
        this.board_title = board_title;
        this.board_description = board_description;
    }
}
