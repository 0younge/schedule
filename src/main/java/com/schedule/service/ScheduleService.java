package com.schedule.service;

import com.schedule.dto.createScheduleRequest;
import com.schedule.dto.createScheduleResponse;
import com.schedule.dto.getScheduleResponse;
import com.schedule.entity.Schedule;
import com.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public createScheduleResponse create(createScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getWriter(),
                request.getPassword());
        scheduleRepository.save(schedule);
        return new createScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public getScheduleResponse getOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));
        return new getScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<getScheduleResponse> getAll() {
        return scheduleRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Schedule::getCreatedAt).reversed())
                .map(schedule -> new getScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<getScheduleResponse> getAllByWriter(String writer) {
        return scheduleRepository.findAllByWriter(writer)
                .stream()
                .sorted(Comparator.comparing(Schedule::getCreatedAt).reversed())
                .map(schedule -> new getScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()))
                .toList();
    }

}
