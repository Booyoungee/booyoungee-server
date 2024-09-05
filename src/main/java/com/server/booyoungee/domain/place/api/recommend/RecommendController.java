package com.server.booyoungee.domain.place.api.recommend;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.place.application.recommend.RecommendService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.dto.response.RecommendPlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.recommend.RecommendPersistResponse;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recommend")
@RequiredArgsConstructor
@Tag(name = "RecommendPlace", description = "장소 추천 api / 관리자 : 이영학")
public class RecommendController {
	private final RecommendService recommendService;

	//ADMIN용
	@PutMapping()
	public void updateAllToNotRecommendPlace() {
		recommendService.updateRecommend();
	}

	@DeleteMapping()
	public ResponseModel<RecommendPersistResponse> deleteRecommend(
		@RequestParam Long id
	) {
		RecommendPersistResponse response = recommendService.deleteRecommend(id);
		return ResponseModel.success(OK, response);
	}

	@Operation(
		summary = "장소 추천 등록",
		description = "장소 추천을 등록합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "추천 여행지 등록 성공",
			content = @Content(schema = @Schema(implementation = RecommendPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_PLACE",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@PostMapping()
	@ResponseStatus(CREATED)
	public ResponseModel<RecommendPersistResponse> addRecommend(

		@RequestParam Long placeId,
		@RequestParam PlaceType type
	) {
		RecommendPersistResponse response = recommendService.addRecommend(placeId, type);
		return ResponseModel.success(CREATED, response);
	}

	//USER 용

	@Operation(
		summary = "추천 장소 조회",
		description = "추천 장소 목록을 조회합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "추천 장소 조회 성공",
			content = @Content(schema = @Schema(implementation = RecommendPlaceListResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_PLACE",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping()
	public ResponseModel<RecommendPlaceListResponse> getRecommendList() throws IOException {

		RecommendPlaceListResponse response = recommendService.getRecommendList();
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

}
