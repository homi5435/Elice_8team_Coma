package com.coma.coma.comments.Repository;

import com.coma.coma.comments.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    private RowMapper<Comment> commentRowMapper(){
        return(rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setCommentId(rs.getInt("comment_id"));
            comment.setUserId(rs.getInt("user_id"));
            comment.setId(rs.getString("id"));
            comment.setPostId(rs.getInt("post_id"));
            comment.setContent(rs.getString("content"));
            comment.setIsDelete(rs.getString("is_delete"));
            comment.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            comment.setModifiedDate(rs.getTimestamp("modified_date") != null
                    ? rs.getTimestamp("modified_date").toLocalDateTime() : null);
            return comment;
        };
    }
    //댓글 추가
    public void addComment(Comment comment) {
            if(comment.getIsDelete() == null) comment.setIsDelete("N");
            String sql = "INSERT INTO Comment (user_id, group_id, id, post_id, content, is_delete, created_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, comment.getUserId());
                ps.setInt(2, comment.getGroupId());
                ps.setString(3, comment.getId());
                ps.setInt(4, comment.getPostId());
                ps.setString(5, comment.getContent());
                ps.setString(6, comment.getIsDelete());
                ps.setTimestamp(7, java.sql.Timestamp.valueOf(comment.getCreatedDate()));
                return ps;
            });
    }

    //특정 게시물 댓글 조회
    public List<Comment> getCommentsByPostId(int postId) {
        String sql = "SELECT * FROM Comment WHERE post_id = ? AND is_delete = 'N'";
        return jdbcTemplate.query(sql, commentRowMapper(), postId);
    }

    //특정 게시물의 댓글 조회(pagination 적용)
    public Page<Comment> getCommentsPageByPostId(int postId, Pageable pageable) {
        int limit = pageable.getPageSize();
        long offset = pageable.getOffset();
        int total = countCommentInPost(postId);

        String sql = "SELECT * FROM Comment WHERE post_id = ? AND is_delete = 'N'"
                + "ORDER BY created_date ASC LIMIT ? OFFSET ?";

        List<Comment> comments = jdbcTemplate.query(sql, commentRowMapper(), postId, limit, offset);
        return new PageImpl<>(comments, pageable, total);
    }

    //게시물에 존재하는 총 데이터 갯수 조회
    public int countCommentInPost(int postId){
        String sql = "SELECT count(*) FROM comment WHERE post_id = ? AND is_delete = 'N'";
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }

    //댓글 아이디를 통해 댓글 조회
    public Comment getCommentById(int commentId) {
        String sql = "SELECT * FROM Comment WHERE comment_id = ? AND is_delete = 'N'";
        return jdbcTemplate.queryForObject(sql, commentRowMapper(), commentId);
    }

    //댓글 수정
    public void updateComment(Comment comment) {
        String sql = "UPDATE Comment SET content = ?, modified_date = ? WHERE comment_id = ?";
        comment.updateModifiedDate();
        jdbcTemplate.update(sql, comment.getContent(), comment.getModifiedDate(), comment.getCommentId());
    }

    //댓글 삭제
    public void deleteComment(int commentId) {
        String sql = "UPDATE Comment SET is_delete = 'Y' WHERE comment_id = ?";
        jdbcTemplate.update(sql, commentId);
    }

    //댓글 아이디를 통해 특정 게시물 아이디 가져오기
    public int getPostIdByCommentId(int commentId) {
        String sql = "SELECT post_id FROM Comment WHERE comment_id = ?";
        Integer postId = jdbcTemplate.queryForObject(sql, Integer.class, commentId);
        return postId != null ? postId : -1;
    }
}
