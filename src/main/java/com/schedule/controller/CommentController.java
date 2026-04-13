package com.schedule.controller;

import com.schedule.dto.CreateCommentRequest;
import com.schedule.dto.CreateCommentResponse;
import com.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 추가 API :
     *
     * 일정 번호에 접근하여 댓글을 작성
     *
     * @param id 일정 번호
     * @param request 댓글 내용, 작성자, 비밀번호 입력
     * @return http 상태코드와 id, 내용, 작성자, 작성/수정 일자 반환
     */
    @PostMapping("/schedules/{id}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@PathVariable Long id, @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(id, request));
    }

}
