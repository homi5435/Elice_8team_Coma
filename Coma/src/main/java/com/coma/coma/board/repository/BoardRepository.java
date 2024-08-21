package com.coma.coma.board.repository;


import com.coma.coma.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {

    List<Board> findAll();

    Optional<Board> findById(Long BoardId);


    Board save(Board board);

    void delete(Board board);
}
