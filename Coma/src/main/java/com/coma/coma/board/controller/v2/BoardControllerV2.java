package com.coma.coma.board.controller.v2;

import com.coma.coma.board.entity.Board;
import com.coma.coma.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v2/boards")
public class BoardControllerV2 {

    private final BoardService boardService;

    public BoardControllerV2(BoardService boardService) {
        this.boardService = boardService;
    }

    // 추가 화면으로 이동
    @GetMapping("/create")
    public String createForm() {
        return "board/v2/createBoard";
    }

    // 수정 화면으로 이동
    @GetMapping("/{board_id}/edit")
    public String updateItemForm(@PathVariable("board_id") Long boardId, Model model) {
        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);

        return "board/v2/editBoard";
    }
}
