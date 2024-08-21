package com.coma.coma.users.repository;

import com.coma.coma.users.domain.Users;
import com.coma.coma.users.dto.UserResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public UserResponseDto getUserByUserId(int user_id) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{user_id}, new RowMapper<UserResponseDto>() {
            @Override
            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserResponseDto user = new UserResponseDto();
                user.setUserId(rs.getInt("user_id"));
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setSignupDate(rs.getTimestamp("signup_date"));
                return user;
            }
        });
    }

    public Optional<Users> findByUserIdName(String id)
    {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try {
            Users user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Users>() {
                @Override
                public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Users user = new Users();
                    user.setUserId(rs.getInt("user_id"));
                    user.setGroupId(rs.getInt("group_id"));
                    user.setId(rs.getString("id"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setIsDelete(rs.getString("is_delete"));
                    user.setModifiedDate(rs.getTimestamp("modified_date"));
                    user.setSignupDate(rs.getTimestamp("signup_date"));
                    return user;
                }
            });
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public void save(Users user) {
        String sql = "INSERT INTO Users (group_id, id, password, name, phone_number, is_delete, modified_date, signup_date) " +
                "VALUES (?, ?, ?, ?, ?, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql, user.getGroupId(), user.getId(), user.getPassword(), user.getName(), user.getPhoneNumber());
    }

    @Override
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM Users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public void update(Users user) {
        String sql = "UPDATE Users SET id = ?, name = ?, phone_number = ?, password = ?, modified_date = CURRENT_TIMESTAMP WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPhoneNumber(), user.getPassword(), user.getUserId());
    }


}
