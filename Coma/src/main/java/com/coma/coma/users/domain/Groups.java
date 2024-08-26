package com.coma.coma.users.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Groups {
    private int groupId;
    private String groupName;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
}
