package com.coma.coma.Service;


import com.coma.coma.Dto.CommentDto;
import com.coma.coma.Entity.Comment;
import com.coma.coma.Repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsByPostId(int commentPostId) {
        return commentRepository.getCommentsByPostId(commentPostId);
    }

    public void addComment(CommentDto commentDto) {
        commentRepository.addComment(commentDto);
    }

    public CommentDto getCommentById(int commentId) {
        return commentRepository.getCommentById(commentId);
    }

    public void updateComment(CommentDto commentDto) {
        commentRepository.updateComment(commentDto);
    }

    public void deleteComment(int commentId) {
        commentRepository.deleteComment(commentId);
    }

    public int getPostIdByCommentId(int commentId) {
        return commentRepository.getPostIdByCommentId(commentId);
    }
}
