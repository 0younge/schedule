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
     * 일정 추가 API:
     * 제목, 내용 작성자, 비밀번호를 입력받아 일정을 추가
     *
     * @param request 제목, 내용, 작성자, 비밀번호 입력
     * @return 일정번호, 제목, 내용, 작성자, 작성/수정일 반환
     */
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(request));
    }

    /**
     * 단건 조회 API:
     * 일정번호로 단건을 조회
     *
     * @param id 조회할 일정 번호
     * @return 일정번호, 제목, 내용, 작성자, 작성/수정일 댓글 반환
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOne(id));
    }

    /**
     * 전체 조회 API:
     * 작성자를 Param으로 받으면 작성자가 작성한 일정 전체 조회 / 아닐 시 전체 일정 조회
     *
     * @param writer 조회할 작성자 입력
     * @return 일정번호, 제목, 내용, 작성자, 작성/수정일 반환
     */
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedule(@RequestParam(required = false) String writer) {
        if (writer != null) {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllByWriter(writer));
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll());
    }

    /**
     * 일정 수정 API:
     * 수정할 일정 번호로 접근해 비밀번호 일치시 제목, 작성자 변경
     *
     * @param id 수정할 일정 번호 입력
     * @param request 수정할 제목, 작성자, 인증을 위한 비밀번호 입력
     * @return 일정번호, 제목, 내용, 작성자, 작성/수정일 반환
     */
    @PutMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(id, request));
    }

    /**
     * 일정 삭제 API:
     * 삭제할 일정번호로 접근해 일정 삭제
     *
     * @param id 삭제할 일정 번호 입력
     * @return 삭제 완료 HttpStatus코드만 반환
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
