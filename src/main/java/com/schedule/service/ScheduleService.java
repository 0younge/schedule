package com.schedule.service;

import com.schedule.dto.*;
import com.schedule.entity.Comment;
import com.schedule.entity.Schedule;
import com.schedule.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    /**
     * 일정 추가 기능
     */
    @Transactional
    public CreateScheduleResponse create(CreateScheduleRequest request) {

        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수로 입력해야합니다.");
        } else if (30 < request.getTitle().length()) {
            throw new IllegalArgumentException("제목은 30자 이내로 작성해야 합니다");
        }

        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수로 입력해야합니다.");
        } else if (100 < request.getContent().length()) {
            throw new IllegalArgumentException("내용은 100자 이내로 작성해야 합니다");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수로 입력해야합니다.");
        }

        if (request.getWriter() == null || request.getWriter().isEmpty()) {
            throw new IllegalArgumentException("작성자명은 필수로 입력해야합니다.");
        }

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
    public GetOneScheduleResponse getOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("없는 일정입니다."));

        List<GetCommentResponse> comments = commentRepository.findAllByScheduleId(id)
                .stream()
                .map(comment -> new GetCommentResponse(
                        comment.getCommentId(),
                        comment.getCommentContent(),
                        comment.getCommentWriter(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()))
                .toList();

        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                comments);
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

        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수로 입력해야합니다.");
        } else if (30 < request.getTitle().length()) {
            throw new IllegalArgumentException("제목은 30자 이내로 작성해야 합니다");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수로 입력해야합니다.");
        }

        if (request.getWriter() == null || request.getWriter().isEmpty()) {
            throw new IllegalArgumentException("작성자명은 필수로 입력해야합니다.");
        }

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
        boolean commentsExistence = commentRepository.findAllByScheduleId(id).isEmpty();
        if (!existence) {
            throw new IllegalStateException("없는 일정입니다.");
        } else if (!commentsExistence) {
            throw new IllegalArgumentException("댓글이 있는 일정은 삭제할 수 없습니다.");
        }
        scheduleRepository.deleteById(id);
    }
}
