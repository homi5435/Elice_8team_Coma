package com.coma.coma.comments.Controller;

import com.coma.coma.comments.Dto.CommentDto;
import com.coma.coma.comments.Entity.Comment;
import com.coma.coma.comments.Service.CommentService;
import com.coma.coma.post.service.PostService;
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
        return "post/post";
    }

    @PostMapping
    public String addComment(@ModelAttribute CommentDto comment) {
        commentService.addComment(comment);
        return "redirect:/page/posts/" + comment.getPostId();
    }

    @GetMapping("/{commentId}/edit")
    public String editCommentForm(@PathVariable int commentId, Model model) {
        CommentDto comment = commentService.getCommentById(commentId);
        model.addAttribute("comment", comment);
        return "post/post";
    }

    @PostMapping("/{commentId}/update")
    public String editComment(@ModelAttribute CommentDto comment) {
        commentService.updateComment(comment);
        int postId = commentService.getPostIdByCommentId(comment.getCommentId());
        return "redirect:/page/posts/" + postId;
    }

    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable int commentId) {
        int postId = commentService.getPostIdByCommentId(commentId);
        commentService.deleteComment(commentId);
        return "redirect:/page/posts/" + postId;
    }
}
