package com.schedulesapp.dto;

import lombok.Getter;

@Getter
public class ScheduleCreateRequest {
    private String title;
    private String content;
    private String author;
    private String password;
}
