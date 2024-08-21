package com.coma.coma.users.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private int userId;
    private String id;
    private String name;
    private String phoneNumber;
    private String password;
    private Timestamp signupDate;
}
