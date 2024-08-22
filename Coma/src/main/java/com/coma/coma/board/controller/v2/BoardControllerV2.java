package com.coma.coma.board.controller.v2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v2/boards")
public class BoardControllerV2 {

    @GetMapping("/create")
    public String createForm() {
        return "board/v2/createBoard";
    }
}
