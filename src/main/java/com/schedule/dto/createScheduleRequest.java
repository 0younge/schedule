package com.schedule.dto;

import lombok.Getter;

@Getter
public class createScheduleRequest {

    private String title;
    private String content;
    private String writer;
    private String password;

}
