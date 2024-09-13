package com.server.booyoungee.domain.bookmark.api;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.bookmark.application.BookMarkService;
import com.server.booyoungee.domain.bookmark.dto.response.BookMarkListResponse;
import com.server.booyoungee.domain.bookmark.dto.response.BookMarkPersistResponse;
import com.server.booyoungee.domain.bookmark.dto.response.BookMarkResponse;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsListResponse;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
@Tag(name = "BookMark", description = "북마크 api / 담당자 : 이영학")
public class BookMarkController {

	private final BookMarkService bookMarkService;

	@Operation(
		summary = "메인화면에서 북마크 조회",
		description = "메인 화면에서 위치와 상관 없이 북마크에 등록된 장소 정보들을 모두가져옵니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "북마크 조회 성공",
			content = @Content(schema = @Schema(implementation = BookMarkResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping
	public ResponseModel<BookMarkListResponse> getBookMarks(
		@UserId User user
	) {
		BookMarkListResponse response = bookMarkService.getBookMarks(user);
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);

	}

	//@Hidden
	@Operation(summary = "마이페이지에서 북마크 조회",
		description = "마이페이지에서 북마크에 등록된 장소들의 상세 정보들을 가져옵니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "북마크 조회 성공",
			content = @Content(schema = @Schema(implementation = PlaceDetailsResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "401",
			description = "Unauthorized(만료된 토큰)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_MovieInfo(영화 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@GetMapping("/me")
	ResponseModel<PlaceDetailsListResponse> getMyBookMarkDetails(
		@UserId User user
	) throws IOException {
		PlaceDetailsListResponse response = PlaceDetailsListResponse.of(bookMarkService.getMyBookMarkDetails(user));
		return response.contents().isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);
	}

	@Operation(
		summary = "북마크 등록",
		description = "조회된 장소중 선택한 장소를 북마크에 등록합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "북마크 등록 성공",
			content = @Content(schema = @Schema(implementation = BookMarkPersistResponse.class))
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
		),
		@ApiResponse(
			responseCode = "409",
			description = "DUPLICATE_BOOKMARK",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@ResponseStatus(CREATED)
	@PostMapping
	ResponseModel<BookMarkPersistResponse> addBookMark(
		@UserId User user,
		@Parameter(
			description = "장소 ID",
			example = "1",
			required = true
		)
		@RequestParam Long placeId,
		@Parameter(
			description = "장소 type",
			example = "tour",
			required = true
		)
		@RequestParam PlaceType type
	) {
		BookMarkPersistResponse response = bookMarkService.addBookMark(user, placeId, type);
		return ResponseModel.success(CREATED, response);
	}

	@Operation(summary = "북마크 삭제", description = "해당 북마크를 삭제합니다.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "북마크 삭제 성공",
			content = @Content(schema = @Schema(implementation = BookMarkPersistResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "NOT_FOUND_TOUR_PLACE (관광지 API 오류)",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "403",
			description = "USER_NOT_HAVE_BOOKMARK",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_BOOKMARK",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		)
	})
	@DeleteMapping
	ResponseModel<BookMarkPersistResponse> deleteBookMark(
		@UserId User user,
		@Parameter(
			description = "장소 ID",
			example = "1",
			required = true
		)
		@RequestParam Long placeId
	) {
		BookMarkPersistResponse response = bookMarkService.deleteBookMark(user, placeId);
		return ResponseModel.success(response);
	}
}
