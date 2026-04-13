package com.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String commentContent;
    private String commentWriter;
    private String commentPassword;

    public Comment(String commentContent, String commentWriter, String commentPassword) {
        this.commentContent = commentContent;
        this.commentWriter = commentWriter;
        this.commentPassword = commentPassword;
    }
}
