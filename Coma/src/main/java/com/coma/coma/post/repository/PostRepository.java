package com.coma.coma.post.repository;

import com.coma.coma.post.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // ROW MAPPER
    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setPostId(rs.getInt("post_id"));
            post.setUserId(rs.getInt("user_id"));    // 추가
            post.setGroupId(rs.getInt("group_id"));  // 추가
            post.setBoardId(rs.getLong("board_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setIsDelete(rs.getString("is_delete"));
            post.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            post.setModifiedDate(rs.getTimestamp("modified_date") != null
                    ? rs.getTimestamp("modified_date").toLocalDateTime()
                    : null);
            return post;
        };
    }

    // CREATE
    public Post create(Post post) {
        String insertSql = "INSERT INTO post (user_id, group_id, board_id, title, content, is_delete, created_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, post.getUserId());         // userId
            ps.setLong(2, post.getGroupId());        // groupId
            ps.setLong(3, post.getBoardId());        // boardId
            ps.setString(4, post.getTitle());        // title
            ps.setString(5, post.getContent());      // content
            ps.setString(6, post.getIsDelete());     // 삭제 여부
            ps.setTimestamp(7, java.sql.Timestamp.valueOf(post.getCreatedDate())); // 생성 시간
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("POST_ID")) {
            post.setPostId(((Number) keys.get("POST_ID")).intValue());  // Set the generated post_id
        }        return post;
    }

    // UPDATE
    public Post update(Post post) {
        String checkDeletedSql = "SELECT is_delete FROM post WHERE post_id = ?";
        String checkDelete = jdbcTemplate.queryForObject(checkDeletedSql, String.class, post.getPostId());

        if("Y".equals(checkDelete)) {
            throw new EmptyResultDataAccessException("삭제된 게시글입니다.", 1);
        }

        String updateSql = "UPDATE post SET title = ?, content = ?, modified_date = ? " +
                "WHERE post_id = ? AND user_id = ?";  // 게시글 작성자 확인 기능
        post.updateModifiedDate();


        jdbcTemplate.update(updateSql,
                post.getTitle(),
                post.getContent(),
                java.sql.Timestamp.valueOf(post.getModifiedDate()),
                post.getPostId(),
                post.getUserId()); // user_id로 게시글 작성자 확인

        return post;
    }

    // SAVE
    public Post save(Post post) {
        if (post.getPostId() == null) {
            return create(post);  // post_id가 없으면 새로 생성
        } else {
            return update(post);  // 있으면 업데이트
        }
    }

    // READ - all
    public List<Post> findAll(Long boardId) {
        String sql = "SELECT * FROM post WHERE board_id = ? AND is_delete = 'N' ORDER BY created_date DESC";
        return jdbcTemplate.query(sql, new Object[]{boardId}, postRowMapper());
    }


    // READ - find By Id
    public Optional<Post> findById(long postId) {
        try {
            Post post = jdbcTemplate.queryForObject(
                    "SELECT * FROM post WHERE post_id = ? AND is_delete = 'N'",
                    postRowMapper(),
                    postId
            );
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // DELETE
    public void delete(Post post) {
        String deleteSql = "UPDATE post SET is_delete = 'Y', modified_date = ? WHERE post_id = ?";
        post.updateModifiedDate();      // update modified date
        jdbcTemplate.update(deleteSql,
                java.sql.Timestamp.valueOf(post.getModifiedDate()),
                post.getPostId());
    }

    public List<Post> findByKeyword(Long boardId, String keyword) {
        String querySql = "SELECT * FROM post " +
                "WHERE board_id = ? " +
                "AND is_delete = 'N' " +
                "AND (Lower(title) LIKE Lower(?) OR Lower(content) LIKE Lower(?))" +
                "ORDER BY created_date DESC";

        return jdbcTemplate.query(
                querySql,
                new Object[]{boardId, "%" + keyword + "%", "%" + keyword + "%"},
                postRowMapper()
        );
    }


    // Pagination 적용
    // READ - pagination
    public List<Post> getPostsWithPagination(Long boardId, int page, int size) {
        int offset = Math.max((page - 1) * size, 0); // 페이지 번호가 1부터 시작하는 경우
        String sql = "SELECT * FROM post " +
                "WHERE board_id = ? " +
                "AND is_delete = 'N' " +
                "ORDER BY created_date DESC " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{boardId, size, offset}, postRowMapper());
    }


    // 게시판에 존재하는 총 데이터 갯수 조회
    public int countPostsInBoard(Long boardId) {
        String sql = "SELECT count(*) FROM post WHERE board_id = ? AND is_delete = 'N'";
        return jdbcTemplate.queryForObject(sql, new Object[]{boardId}, Integer.class);
    }

    // 검색 결과 총 데이터 갯수 조회
    public int countPostsInSearchResult(Long boardId, String keyword) {
        String sql = "SELECT count(*) FROM post " +
                "WHERE board_id = ? " +
                "AND is_delete = 'N' " +
                "AND (Lower(title) LIKE Lower(?) OR Lower(content) LIKE Lower(?))";

        return jdbcTemplate.queryForObject(sql, new Object[]{
                boardId,
                "%" + keyword + "%",
                "%" + keyword + "%"
        }, Integer.class);
    }

    // search - with keyword and pagination
    public List<Post> findByKeywordWithPagination(Long boardId, String keyword, int page, int size) {
        int offset = Math.max((page - 1) * size, 0);
        String sql = "SELECT * FROM post " +
                "WHERE board_id = ? " +
                "AND is_delete = 'N' " +
                "AND (Lower(title) LIKE Lower(?) OR Lower(content) LIKE Lower(?)) " +
                "ORDER BY created_date DESC " +
                "LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new Object[]{
                boardId,
                "%" + keyword + "%",
                "%" + keyword + "%",
                size,
                offset
        }, postRowMapper());
    }
}
