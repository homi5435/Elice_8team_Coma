package com.coma.coma.comments.Service;


import com.coma.coma.comments.Dto.CommentDto;
import com.coma.coma.comments.Entity.Comment;
import com.coma.coma.comments.Repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsByPostId(int postId) {
        return commentRepository.getCommentsByPostId(postId);
    }

    public void addComment(Comment comment) {
        commentRepository.addComment(comment);
    }

    public Comment getCommentById(Integer commentId) {
        return commentRepository.getCommentById(commentId);
    }

    public void updateComment(Comment comment) {
        commentRepository.updateComment(comment);
    }

    public void deleteComment(Integer commentId) {
        commentRepository.deleteComment(commentId);
    }

    public int getPostIdByCommentId(Integer commentId) {
        return commentRepository.getPostIdByCommentId(commentId);
    }

    public boolean commentOwner(Integer commentId, Integer userId){
        Comment comment = commentRepository.getCommentById(commentId);
        return comment.getUserId().equals(userId);
    }
}
