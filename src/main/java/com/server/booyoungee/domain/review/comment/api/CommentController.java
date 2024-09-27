package com.server.booyoungee.domain.review.comment.api;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.review.comment.application.CommentService;
import com.server.booyoungee.domain.review.comment.dto.request.CommentRequest;
import com.server.booyoungee.domain.review.comment.dto.response.CommentListResponse;
import com.server.booyoungee.domain.review.comment.dto.response.CommentPersistResponse;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 코멘트 관련 api / 담당자 : 이한음")
public class CommentController {
	private final CommentService commentService;

	@Operation(summary = "리뷰 코멘트 작성", description = "장소에 대한 리뷰 코멘트를 작성합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "리뷰 코멘트 작성 성공",
			content = @Content(schema = @Schema(implementation = CommentPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "INVALID_STAR_VALUE",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_PLACE",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "409",
			description = "DUPLICATED",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
			),
	})
	@ResponseStatus(CREATED)
	@PostMapping
	public ResponseModel<CommentPersistResponse> saveReview(
		@UserId User user,
		@Valid @RequestBody CommentRequest request
	) {
		CommentPersistResponse response = commentService.saveReview(user, request);
		return ResponseModel.success(CREATED, response);
	}

	@Operation(summary = "리뷰 코멘트 신고하기", description = "리뷰 코멘트를 신고합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "리뷰 코멘트 신고 성공",
			content = @Content(schema = @Schema(implementation = CommentPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_COMMENT",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@ResponseStatus(CREATED)
	@PostMapping("/{commentId}/accuse")
	public ResponseModel<CommentPersistResponse> accuseReview(
		@UserId User user,
		@Parameter(
			description = "리뷰 코멘트 ID",
			example = "1",
			required = true
		)
		@PathVariable(name = "commentId") Long commentId
	) {
		CommentPersistResponse response = commentService.accuseReview(user, commentId);
		return ResponseModel.success(CREATED, response);
	}

	@Operation(summary = "장소에 대한 리뷰 코멘트 목록 조회", description = "장소에 대한 리뷰 코멘트 목록을 조회합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "장소에 대한 리뷰 코멘트 목록 조회 성공",
			content = @Content(schema = @Schema(implementation = CommentListResponse.class))
		)
	})
	@GetMapping("/{placeId}")
	public ResponseModel<CommentListResponse> getReviewList(
		@UserId User user,
		@Parameter(
			description = "장소 ID",
			example = "1",
			required = true
		)
		@PathVariable(name = "placeId") Long placeId
	) {
		CommentListResponse response = commentService.getReviewList(placeId, user);
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

	@Operation(summary = "내가 쓴 리뷰 코멘트 목록 조회", description = "내가 쓴 리뷰 코멘트 목록을 조회합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "내가 쓴 리뷰 코멘트 목록 조회 성공",
			content = @Content(schema = @Schema(implementation = CommentListResponse.class))
		)
	})
	@GetMapping("/my")
	public ResponseModel<CommentListResponse> getMyReviewList(
		@UserId User user
	) {
		CommentListResponse response = commentService.getMyReviewList(user);
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

	@Operation(summary = "리뷰 코멘트 삭제", description = "리뷰 코멘트를 삭제합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "리뷰 코멘트 삭제 성공",
			content = @Content(schema = @Schema(implementation = CommentPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "403",
			description = "USER_NOT_WRITER_OF_COMMENT",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_COMMENT",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@DeleteMapping("/{commentId}")
	public ResponseModel<CommentPersistResponse> deleteReview(
		@UserId User user,
		@Parameter(
			description = "리뷰 코멘트 ID",
			example = "1",
			required = true
		)
		@PathVariable(name = "commentId") Long commentId
	) {
		CommentPersistResponse response = commentService.deleteReview(user, commentId);
		return ResponseModel.success(response);
	}

}
