package com.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String password;

    public Schedule(String title, String content, String writer, String password) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
    }

    public void updateSchedule(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }

}
