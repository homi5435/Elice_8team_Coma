package com.coma.coma.board.controller.v2;


import com.coma.coma.board.dto.BoardCreateRequest;
import com.coma.coma.board.entity.Board;
import com.coma.coma.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/boards")
public class BoardApiController {

    private final BoardService boardService;

    public BoardApiController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping()
    public ResponseEntity<Void> createBoard(@RequestBody @Validated BoardCreateRequest boardCreateRequest) {
        Board board = boardCreateRequest.toEntity();
        boardService.createBoard(board);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
