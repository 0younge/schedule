package com.schedule.controller;

import com.schedule.dto.createScheduleRequest;
import com.schedule.dto.createScheduleResponse;
import com.schedule.dto.getScheduleResponse;
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

    @PostMapping("/schedules")
    public ResponseEntity<createScheduleResponse> createSchedule(@RequestBody createScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(request));
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<getScheduleResponse> getOneSchedule(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(id));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<getScheduleResponse>> getAllSchedule(@RequestParam(required = false) String writer) {
        if (writer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllByWriter(writer));
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll());
    }

}
