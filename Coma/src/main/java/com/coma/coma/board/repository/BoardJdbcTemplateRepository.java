package com.coma.coma.board.repository;

import com.coma.coma.board.entity.Board;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardJdbcTemplateRepository  implements BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public BoardJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setBoard_id(rs.getLong("board_id"));
            board.setUser_id(rs.getLong("user_id"));
            board.setBoard_title(rs.getString("board_title"));
            board.setBoard_description(rs.getString("board_description"));
            board.setIs_delete(rs.getString("is_delete"));
            board.setCreated_date(rs.getTimestamp("created_date").toLocalDateTime());
            board.setModified_date(rs.getTimestamp("modified_date")!= null
                    ? rs.getTimestamp("modified_date").toLocalDateTime()
                    : null)
            ;
            return board;
        };
    }

    @Override
    public List<Board> findAll() {
        return jdbcTemplate.query("select * from board where is_delete = 'N'", boardRowMapper());
    }

    @Override
    public Optional<Board> findById(Long BoardId) {
        try {
            Board board = jdbcTemplate.queryForObject(
                    "SELECT * FROM board WHERE board_id = ? and is_delete = 'N'",
                    boardRowMapper(),
                    BoardId
            );
            return Optional.ofNullable(board);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void upsert(Board board) {

        if (board.getBoard_id() == null) {
            // Insert new contact and retrieve generated contactId
            KeyHolder keyHolder = new GeneratedKeyHolder();
            // TODO insert시 생성일 컬럼값 추가
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO board (board_title, board_description, created_date, user_id) VALUES (?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS
                        );
                        ps.setString(1, board.getBoard_title());
                        ps.setString(2, board.getBoard_description());
                        ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                        ps.setLong(4, board.getUser_id());
                        return ps;
                    },
                    keyHolder
            );
            // Set the generated contactId to the contact object
            Number key = keyHolder.getKey();
            if (key != null) {
                board.setBoard_id(key.longValue());
            } else {
                // Handle the scenario where key generation failed
                throw new RuntimeException("Failed to generate boardId for new board");
            }
        } else {
            // Update existing contact
            jdbcTemplate.update(
                    "UPDATE board SET board_title = ?, board_description = ?, modified_date = ?  WHERE board_id = ?",
                    board.getBoard_title(), board.getBoard_description(), Timestamp.valueOf(LocalDateTime.now()), board.getBoard_id()
            );
        }
    }

    @Override
    public void delete(Board board) {
        jdbcTemplate.update(
                "UPDATE board SET is_delete = 'Y' WHERE board_id = ?",
                board.getBoard_id()
        );
    }
}
