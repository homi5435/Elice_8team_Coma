package com.coma.coma.comments.Repository;

import com.coma.coma.comments.Dto.CommentDto;
import com.coma.coma.comments.Entity.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addComment(Comment comment) {
            if(comment.getIsDelete() == null) comment.setIsDelete("N");
            String sql = "INSERT INTO Comment (user_id, group_id, post_id, content, is_delete, created_date) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, comment.getUserId());
                ps.setInt(2, comment.getGroupId());
                ps.setInt(3, comment.getPostId());
                ps.setString(4, comment.getContent());
                ps.setString(5, comment.getIsDelete());
                ps.setTimestamp(6, java.sql.Timestamp.valueOf(comment.getCreatedDate()));
                return ps;
            });
    }

    public List<Comment> getCommentsByPostId(int postId) {
        String sql = "SELECT * FROM Comment WHERE post_id = ? AND is_delete = 'N'";
        RowMapper<Comment> rowMapper = (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setCommentId(rs.getInt("comment_id"));
            comment.setUserId(rs.getInt("user_id"));
            comment.setPostId(rs.getInt("post_id"));
            comment.setContent(rs.getString("content"));
            comment.setIsDelete(rs.getString("is_delete"));
            comment.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            comment.setModifiedDate(rs.getTimestamp("modified_date") != null
                    ? rs.getTimestamp("modified_date").toLocalDateTime() : null);
            return comment;
        };
        return jdbcTemplate.query(sql, rowMapper, postId);
    }

    public CommentDto getCommentById(int commentId) {
        String sql = "SELECT * FROM Comment WHERE comment_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            CommentDto comment = new CommentDto();
            comment.setCommentId(rs.getInt("comment_id"));
            comment.setUserId(rs.getInt("user_id"));
            comment.setPostId(rs.getInt("post_id"));
            comment.setContent(rs.getString("content"));
            comment.setIsDelete(rs.getString("is_delete"));
            comment.setModifiedDate(rs.getTimestamp("modified_date").toLocalDateTime());
            comment.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            return comment;
        }, commentId);
    }

    public void updateComment(Comment comment) {
        String sql = "UPDATE Comment SET content = ?, modified_date = ? WHERE comment_id = ?";
        comment.updateModifiedDate();
        jdbcTemplate.update(sql, comment.getContent(), comment.getModifiedDate(), comment.getCommentId());
    }

    public void deleteComment(int commentId) {
        String sql = "UPDATE Comment SET is_delete = 'Y' WHERE comment_id = ?";
        jdbcTemplate.update(sql, commentId);
    }

    public int getPostIdByCommentId(int commentId) {
        String sql = "SELECT post_id FROM Comment WHERE comment_id = ?";
        Integer postId = jdbcTemplate.queryForObject(sql, Integer.class, commentId);
        return postId != null ? postId : -1;
    }
}
