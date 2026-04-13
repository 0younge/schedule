package com.schedule.service;

import com.schedule.dto.CreateCommentRequest;
import com.schedule.dto.CreateCommentResponse;
import com.schedule.entity.Comment;
import com.schedule.entity.Schedule;
import com.schedule.repository.CommentRepository;
import com.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateCommentResponse create(Long id, CreateCommentRequest request) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalStateException("없는 일정입니다.");
        }

        int commentCount = commentRepository.countByScheduleId(id);
        if (10 <= commentCount) {
            throw new IllegalStateException("일정 당 10개까지만 댓글을 남길 수 있습니다,");
        }

        if (request.getCommentContent() == null || request.getCommentContent().isEmpty()) {
            throw new IllegalArgumentException("빈 값을 등록할 수 없습니다.");
        } else if (100 < request.getCommentContent().length()) {
            throw new IllegalArgumentException("100자 이내로 입력해야 합니다");
        }

        Comment comment = new Comment(request.getCommentContent(), request.getCommentWriter(), request.getCommentPassword(), id);
        commentRepository.save(comment);

        return new CreateCommentResponse(
                comment.getCommentId(),
                comment.getCommentContent(),
                comment.getCommentWriter(),
                comment.getCreatedAt(),
                comment.getModifiedAt());
    }
}
