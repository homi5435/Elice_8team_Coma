package com.coma.coma.users.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    private int userId;
    private int groupId;
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String isDelete;
    private Timestamp modifiedDate;
    private Timestamp signupDate;
}
