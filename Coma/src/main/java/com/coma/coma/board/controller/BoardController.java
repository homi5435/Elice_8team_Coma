package com.coma.coma.board.controller;

import com.coma.coma.board.dto.BoardCreateRequest;
import com.coma.coma.board.dto.BoardUpdateRequest;
import com.coma.coma.board.entity.Board;
import com.coma.coma.board.service.BoardService;
import com.coma.coma.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
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
    public String getBoards(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        List<Board> boards = boardService.findBoards();
        model.addAttribute("boards", boards);
        model.addAttribute("user", customUserDetails);
        return "board/boards";
    }

    /*
    // 개발 초창기에 정의한 /api/boards get요청(게시판 목록으로 화면이동)을 게시글, 로그인 화면등 다른곳에서 사용중
    // 그렇기 때문에 v2패키지 하위에 뷰 컨트롤러(/v2/boards)로 전환하지 않고 기존 url을 사용
    // 아래 주석된 내용은 현재 사용안함(v2 패키지에 apiController, viewController로 분리함)

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
    public String createBoard(@ModelAttribute("form") @Validated BoardCreateRequest boardCreateRequest) {
        Board board = boardCreateRequest.toEntity();
        boardService.createBoard(board);
        return "redirect:/api/boards";
    }

    // 수정 api
    @PostMapping("/{boardId}/edit")
    public String updateContact(@PathVariable("boardId") Long boardId, @ModelAttribute("form") @Validated BoardUpdateRequest boardUpdateRequest) {
        boardService.updateBoard(boardId, boardUpdateRequest);
        return "redirect:/api/boards";
    }

    // 삭제 api
    @DeleteMapping("/{currentBoard}")
    public String deleteBoard(@PathVariable("currentBoard") long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/api/boards";
    }

     */

}
