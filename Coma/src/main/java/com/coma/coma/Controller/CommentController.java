package com.coma.coma.Controller;

import com.coma.coma.Dto.CommentDto;
import com.coma.coma.Entity.Comment;
import com.coma.coma.Service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}")
    public String getComments(@PathVariable int postId, Model model) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        model.addAttribute("postId", postId);
        return "comments";
    }

    @PostMapping
    public String addComment(@ModelAttribute CommentDto commentDto) {
        commentService.addComment(commentDto);
        return "redirect:/comments/" + commentDto.getCommentPostId(); // 댓글 추가 후 해당 게시글의 댓글 목록으로 리다이렉트
    }

    @GetMapping("/edit/{commentId}")
    public String editCommentForm(@PathVariable int commentId, Model model) {
        CommentDto comment = commentService.getCommentById(commentId);
        model.addAttribute("comment", comment);
        return "editComment";
    }

    @PostMapping("/edit")
    public String editComment(@ModelAttribute CommentDto commentDto) {
        commentService.updateComment(commentDto);
        return "redirect:/comments/" + commentDto.getCommentPostId(); // 수정 후 해당 게시글의 댓글 목록으로 리다이렉트
    }

    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable int commentId) {
        int postId = commentService.getPostIdByCommentId(commentId);
        commentService.deleteComment(commentId);
        return "redirect:/comments/" + postId; // 삭제 후 해당 게시글의 댓글 목록으로 리다이렉트
    }
}
