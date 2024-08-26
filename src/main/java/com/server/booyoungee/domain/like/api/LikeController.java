package com.server.booyoungee.domain.like.api;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.like.application.LikeService;
import com.server.booyoungee.domain.like.dto.request.LikeRequest;
import com.server.booyoungee.domain.like.dto.response.LikePersistResponse;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
@Tag(name = "Like", description = "좋아요 관련 api / 담당자 : 이한음")
public class LikeController {
	private final LikeService likeService;

	@Operation(summary = "좋아요 등록", description = "장소에 대한 좋아요를 등록합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "좋아요 등록 성공",
			content = @Content(schema = @Schema(implementation = LikePersistResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_PLACE",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
	})
	@ResponseStatus(CREATED)
	@PostMapping
	public ResponseModel<LikePersistResponse> saveLike(
		@UserId User user,
		@Valid @RequestBody LikeRequest request
	) {
		LikePersistResponse response = likeService.saveLike(user, request);
		return ResponseModel.success(CREATED, response);
	}

}
