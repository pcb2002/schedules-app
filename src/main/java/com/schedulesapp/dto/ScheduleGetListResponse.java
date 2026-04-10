package com.schedulesapp.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class ScheduleGetListResponse {
    private List<ScheduleGetResponse> schedules;

    public ScheduleGetListResponse(List<ScheduleGetResponse> schedules) {
        this.schedules = schedules;
    }
}
