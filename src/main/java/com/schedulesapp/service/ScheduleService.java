package com.schedulesapp.service;

import com.schedulesapp.dto.*;
import com.schedulesapp.entity.Schedule;
import com.schedulesapp.exception.CustomException;
import com.schedulesapp.exception.ErrorCode;
import com.schedulesapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleCreateResponse saveSchedule(ScheduleCreateRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                request.getPassword()
        );
        Schedule saved = scheduleRepository.save(schedule);
        return new ScheduleCreateResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getAuthor(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public ScheduleGetListResponse getAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleGetResponse> dtos = new ArrayList<>();

//      List<ScheduleGetResponse> dtos = schedules.stream()
//            .map(ScheduleGetResponse::new)
//            .toList();

        for (Schedule schedule : schedules) {
            ScheduleGetResponse dto = new ScheduleGetResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getAuthor(),
                    schedule.getCreatedAt(),
                    schedule.getUpdatedAt()
            );
            dtos.add(dto);
        }
        return new ScheduleGetListResponse(dtos);
    }

    @Transactional(readOnly = true)
    public ScheduleGetResponse getOneSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        return new ScheduleGetResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    @Transactional
    public ScheduleUpdateResponse updateSchedule(Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        schedule.checkPassword(request.getPassword());
        schedule.update(
                request.getTitle(),
                request.getAuthor()
        );
        return new ScheduleUpdateResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, ScheduleDeleteRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND)
        );
        schedule.checkPassword(request.getPassword());
        scheduleRepository.delete(schedule);
    }
}
