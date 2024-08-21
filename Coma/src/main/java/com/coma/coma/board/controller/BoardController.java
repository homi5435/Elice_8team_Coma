package com.coma.coma.board.controller;

import com.coma.coma.board.entity.Board;
import com.coma.coma.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    // 게시판 리스트 화면 이동
    @GetMapping
    public String getBoards(Model model) {
        List<Board> boards = boardService.findBoards();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    // 게시판 추가 화면 이동
    @GetMapping("/create")
    public String createForm() {
        return "board/createBoard";
    }

    // 게시판 수정 화면 이동
    @GetMapping("/{board_id}/edit")
    public String updateItemForm(@PathVariable("board_id") Long boardId, Model model) {
        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);

        return "board/editBoard";
    }

    // 추가 api
    @PostMapping()
    public String createBoard(@ModelAttribute("form") Board board, Model model) {
        boardService.createBoard(board);
        return "redirect:/api/boards";
    }

    // 수정 api
    @PostMapping("/{boardId}/edit")
    public String updateContact(@PathVariable("boardId") long boardId, @ModelAttribute("form") Board board) {
        board.setBoard_id(boardId);
        boardService.updateBoard(board);
        return "redirect:/api/boards";
    }

    // 삭제 api
    @DeleteMapping("/{currentBoard}")
    public String deleteBoard(@PathVariable("currentBoard") long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/api/boards";
    }

}
