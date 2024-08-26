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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    public String addComment(@ModelAttribute CommentDto commentDto, @Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null){
            // 로그인이 안되었을 경우 로그인 페이지로 이동
            return "redirect:/users/login";
        } else {
            int userId = customUserDetails.getUserId();
            UserResponseDto userResponseDto = userService.getUserByUserId(userId);
            Comment comment = commentMapper.toEntity(commentDto);
            comment.setUserId(userId);
            comment.setGroupId(customUserDetails.getGroupId());
            comment.setId(userResponseDto.getId());
            commentService.addComment(comment);
            return "redirect:/page/posts/" + comment.getPostId();
        }
    }

    @PreAuthorize("commentService.commentOwner(#commentId, #customUserDetails.userId)")
    @PostMapping("/{commentId}/update")
    public String updateComment(@ModelAttribute CommentDto commentDto,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Comment comment = commentMapper.toEntity(commentDto);
        int userId = commentService.getCommentById(comment.getCommentId()).getUserId();
        UserResponseDto commentAuthor = userService.getUserByUserId(userId);
        int postId = commentService.getPostIdByCommentId(comment.getCommentId());

        if (customUserDetails != null && customUserDetails.getUserId() == commentAuthor.getUserId()){
            commentService.updateComment(comment);
        }
        return "redirect:/page/posts/" + postId;

    }

    @PreAuthorize("commentService.commentOwner(#commentId, #customUserDetails.userId) or #customUserDetails.groupId = 1")
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable int commentId,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Comment comment = commentService.getCommentById(commentId);
        int postId = commentService.getPostIdByCommentId(commentId);
        UserResponseDto commentAuthor = userService.getUserByUserId(comment.getUserId());

        if (customUserDetails != null){
            if (customUserDetails.getUserId() == commentAuthor.getUserId() || customUserDetails.getGroupId() == 1){
                commentService.deleteComment(commentId);
            }
        }
        return "redirect:/page/posts/" + postId;
    }
}
