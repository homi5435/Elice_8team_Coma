package com.coma.coma.board.controller.v2;


import com.coma.coma.board.dto.BoardCreateRequest;
import com.coma.coma.board.dto.BoardUpdateRequest;
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


    // 추가 api
    @PostMapping()
    public ResponseEntity<Void> createBoard(@RequestBody @Validated BoardCreateRequest boardCreateRequest) {
        Board board = boardCreateRequest.toEntity();
        boardService.createBoard(board);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 수정 api
    @PutMapping("/{boardId}")
    public ResponseEntity<Void> updateContact(@PathVariable("boardId") Long boardId, @RequestBody @Validated BoardUpdateRequest boardUpdateRequest) {
        boardService.updateBoard(boardId, boardUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 삭제 api
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
