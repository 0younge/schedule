package com.schedule.service;

import com.schedule.dto.*;
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

    /**
     * 일정 추가 기능
     */
    @Transactional
    public CreateScheduleResponse create(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getWriter(),
                request.getPassword());
        scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    /**
     * 일정 조회 기능
     */
    // 단건 조회
    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    // 다건 조회
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAll() {
        return scheduleRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Schedule::getCreatedAt).reversed())
                .map(schedule -> new GetScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()))
                .toList();
    }

    // 작성자 별 다건 조회
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAllByWriter(String writer) {
        return scheduleRepository.findAllByWriter(writer)
                .stream()
                .sorted(Comparator.comparing(Schedule::getCreatedAt).reversed())
                .map(schedule -> new GetScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getWriter(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()))
                .toList();
    }

    /**
     * 일정 수정 기능
     */
    @Transactional
    public UpdateScheduleResponse update(Long id, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));
        if (schedule.getPassword().equals(request.getPassword())) {
            schedule.updateSchedule(request.getTitle(), request.getWriter());
        } else {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다,");
        }
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
    }

    /**
     * 일정 삭제 기능
     */
    @Transactional
    public void delete(Long id) {
        boolean existence = scheduleRepository.existsById(id);
        if (!existence) {
            throw new IllegalStateException("없는 일정입니다.");
        }
        scheduleRepository.deleteById(id);
    }
}
