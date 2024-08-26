package com.coma.coma.board.service;

import com.coma.coma.board.dto.BoardUpdateRequest;
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

    public void createBoard(Board board) {
        boardRepository.upsert(board);
    }

    public void updateBoard(Long boardId, BoardUpdateRequest boardUpdateRequest) {
        Board targetBoard = findOne(boardId);

        targetBoard.setBoard_title(boardUpdateRequest.getBoard_title());
        targetBoard.setBoard_description(boardUpdateRequest.getBoard_description());

        boardRepository.upsert(targetBoard);
    }
    public void deleteBoard(long boardId) {

        Board targetBoard = findOne(boardId);
        boardRepository.delete(targetBoard);
    }
}
