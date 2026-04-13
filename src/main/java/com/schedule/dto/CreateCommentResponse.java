package com.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final Long commentId;
    private final String commentContent;
    private final String commentWriter;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public CreateCommentResponse(Long commentId, String commentContent, String commentWriter, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentWriter = commentWriter;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
