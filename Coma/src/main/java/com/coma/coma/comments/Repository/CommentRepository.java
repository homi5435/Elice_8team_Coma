package com.coma.coma.comments.Repository;

import com.coma.coma.comments.Dto.CommentDto;
import com.coma.coma.comments.Entity.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addComment(CommentDto comment) {
        String sql = "INSERT INTO Comment (comment_user_id, comment_post_id, content, is_delete, created_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, comment.getCommentUserId());
            ps.setInt(2, comment.getCommentPostId());
            ps.setString(3, comment.getContent());
            ps.setString(4, comment.getIsDelete());
            ps.setTimestamp(5, comment.getCreatedDate());
            return ps;
        });
    }

    public List<Comment> getCommentsByPostId(int postId) {
        String sql = "SELECT * FROM Comment WHERE comment_post_id = ? AND is_delete = 'N'";
        RowMapper<Comment> rowMapper = (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setCommentId(rs.getInt("comment_id"));
            comment.setCommentUserId(rs.getInt("comment_user_id"));
            comment.setCommentPostId(rs.getInt("post_id"));
            comment.setContent(rs.getString("content"));
            comment.setIsDelete(rs.getString("is_delete"));
            comment.setModifiedDate(rs.getTimestamp("modified_date"));
            comment.setCreatedDate(rs.getTimestamp("created_date"));
            return comment;
        };
        return jdbcTemplate.query(sql, rowMapper, new Object[]{postId});
    }

    public CommentDto getCommentById(int commentId) {
        String sql = "SELECT * FROM Comment WHERE comment_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            CommentDto comment = new CommentDto();
            comment.setCommentId(rs.getInt("comment_id"));
            comment.setCommentUserId(rs.getInt("comment_user_id"));
            comment.setCommentPostId(rs.getInt("post_id"));
            comment.setContent(rs.getString("content"));
            comment.setIsDelete(rs.getString("is_delete"));
            comment.setModifiedDate(rs.getTimestamp("modified_date"));
            comment.setCreatedDate(rs.getTimestamp("created_date"));
            return comment;
        }, new Object[]{commentId});
    }

    public void updateComment(CommentDto comment) {
        String sql = "UPDATE Comment SET content = ?, modified_date = ? WHERE comment_id = ?";
        jdbcTemplate.update(sql, comment.getContent(), comment.getModifiedDate(), comment.getCommentId());
    }

    public void deleteComment(int commentId) {
        String sql = "UPDATE Comment SET is_delete = 'Y' WHERE comment_id = ?";
        jdbcTemplate.update(sql, commentId);
    }

    public int getPostIdByCommentId(int commentId) {
        String sql = "SELECT comment_post_id FROM Comment WHERE comment_id = ?";
        Integer postId = jdbcTemplate.queryForObject(sql,  Integer.class, new Object[]{commentId});
        return postId != null ? postId : -1;
    }
}
