package com.coma.coma.comments.Entity;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Timestamp;

@Entity
@Table(name = "Comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "comment_user_id", nullable = false)
    private int commentUserId;

    @Column(name = "comment_post_id", nullable = false)
    private int commentPostId;

    @Column(name = "content")
    private String content;

    @Column(name = "is_delete")
    private String isDelete;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Column(name = "created_date")
    private Timestamp createdDate;
}
