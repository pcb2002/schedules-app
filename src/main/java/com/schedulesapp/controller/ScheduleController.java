package com.schedulesapp.controller;

import com.schedulesapp.dto.CreateScheduleRequest;
import com.schedulesapp.dto.CreateScheduleResponse;
import com.schedulesapp.dto.GetScheduleResponse;
import com.schedulesapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> create(@RequestBody CreateScheduleRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAll() {
        return  ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll());
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOne(@PathVariable Long scheduleId){
        return  ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(scheduleId));
    }



}
