package com.server.booyoungee.domain.review.comment.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.review.comment.application.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 코멘트 관련 api / 담당자 : 이한음")
public class CommentController {
	private final CommentService commentService;
}
