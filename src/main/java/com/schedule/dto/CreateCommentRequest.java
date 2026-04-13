package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private String commentContent;
    private String commentWriter;
    private String commentPassword;

}
