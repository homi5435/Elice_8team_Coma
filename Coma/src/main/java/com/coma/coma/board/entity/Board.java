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
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

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
}
