package com.coma.coma.users.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserResponseDto {
    private int userId;
    private String id;
    private String name;
    private String phoneNumber;
    private String password;
    private Timestamp signupDate;
}
