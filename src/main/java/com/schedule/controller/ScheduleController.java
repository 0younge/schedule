package com.schedule.controller;

import com.schedule.dto.*;
import com.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 추가API
     */
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(request));
    }

    /**
     * 일정 조회API
     */
    // 단건 조회
    @GetMapping("/schedules/{id}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(id));
    }

    // 전체 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedule(@RequestParam(required = false) String writer) {
        if (writer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllByWriter(writer));
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll());
    }

    /**
     * 일정 수정API
     */
    @PutMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(id, request));
    }

    /**
     * 일정 삭제API
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
