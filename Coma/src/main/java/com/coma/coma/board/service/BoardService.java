package com.coma.coma.board.service;

import com.coma.coma.board.entity.Board;
import com.coma.coma.board.repository.BoardJdbcTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardJdbcTemplateRepository boardRepository;

    public BoardService(BoardJdbcTemplateRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException());
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public void updateBoard(Board board) {
        Board targetBoard = findOne(board.getBoard_id());

        targetBoard.setBoard_title(board.getBoard_title());
        targetBoard.setBoard_description(board.getBoard_description());


        boardRepository.save(targetBoard);
    }
    public void deleteBoard(long boardId) {

        Board targetBoard = findOne(boardId);
        boardRepository.delete(targetBoard);
    }
}
