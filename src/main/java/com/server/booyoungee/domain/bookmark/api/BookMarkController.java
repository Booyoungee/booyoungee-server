package com.server.booyoungee.domain.bookmark.api;

import static org.springframework.http.HttpStatus.*;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.bookmark.application.BookMarkService;
import com.server.booyoungee.domain.bookmark.dto.response.BookMarkPersistResponse;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkResponse;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;
import com.server.booyoungee.global.exception.ExceptionResponse;

import io.swagger.v3.oas.annotations.Hidden;
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
			content = @Content(schema = @Schema(implementation = TourInfoBookMarkResponse.class))
		)
	})
	@GetMapping("")
	public ResponseModel<List<TourInfoBookMarkResponse>> getBookMarks(
		@Parameter(hidden = true) @UserId User user
	) {
		List<TourInfoBookMarkResponse> response = bookMarkService.getBookMarks(user);
		return response.isEmpty()
			? ResponseModel.success(NO_CONTENT, response)
			: ResponseModel.success(response);

	}

	@Hidden
	@Operation(summary = "마이페이지에서 북마크 조회",
		description = "마이페이지에서 북마크에 등록된 장소들의 상세 정보들을 가져옵니다."
	)
	@GetMapping("/me")
	ResponseModel<List<PlaceDetailsResponse>> getMyBookMarkDetails(
		@Parameter(hidden = true) @UserId User user
	) throws IOException {
		return ResponseModel.success(bookMarkService.getMyBookMarkDetails(user));
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
			responseCode = "409",
			description = "DUPLICATE_BOOKMARK",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "NOT_FOUND_PLACE",
			content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
		),
	})
	@ResponseStatus(CREATED)
	@PostMapping("")
	ResponseModel<BookMarkPersistResponse> addBookMark(
		@Parameter(hidden = true) @UserId User user,
		@RequestParam Long placeId,
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

	@DeleteMapping("")
	ResponseModel<BookMarkPersistResponse> deleteBookMark(
		@Parameter(hidden = true) @UserId User user,
		@RequestParam Long bookMarkId
	) {
		BookMarkPersistResponse response = bookMarkService.deleteBookMark(user, bookMarkId);
		return ResponseModel.success(response);
	}
}
