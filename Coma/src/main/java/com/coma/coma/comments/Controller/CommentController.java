package com.coma.coma.comments.Controller;

import com.coma.coma.comments.Dto.CommentDto;
import com.coma.coma.comments.Entity.Comment;
import com.coma.coma.comments.Mapper.CommentMapper;
import com.coma.coma.comments.Service.CommentService;
import com.coma.coma.post.service.PostService;
import com.coma.coma.security.CustomUserDetails;
import com.coma.coma.users.dto.UserResponseDto;
import com.coma.coma.users.service.UserService;
import jakarta.annotation.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, UserService userService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{postId}")
    public String getComments(@PathVariable int postId, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        model.addAttribute("postId", postId);
        model.addAttribute("user", customUserDetails);
        return "post/post";
    }

    @PostMapping
    public String addComment(@ModelAttribute CommentDto commentDto, @Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null){
            return "redirect:/users/login";
        }
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setUserId(customUserDetails.getUserId());
        comment.setGroupId(customUserDetails.getGroupId());
        commentService.addComment(comment);
        return "redirect:/page/posts/" + comment.getPostId();
    }

    @PostMapping("/{commentId}/update")
    public String editComment(@ModelAttribute CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
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
