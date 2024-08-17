package com.server.booyoungee.domain.bookmark.api;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.bookmark.application.BookMarkService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkResponse;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
@Tag(name = "BookMark", description = "북마크 api / 담당자 : 이영학")
public class BookMarkController {

	private final BookMarkService bookMarkService;
	@Operation(summary = "메인화면에서 북마크 조회")
	@GetMapping("")
	public ResponseModel<List<TourInfoBookMarkResponse>> getBookMarks(
		@Parameter(hidden = true) @UserId User user
	){
		return ResponseModel.success(bookMarkService.getBookMarks(user));
	}
	@Hidden
	@Operation(summary = "마이페이지에서 북마크 조회")
	@GetMapping("/me")
	ResponseModel<List<PlaceDetailsResponse>> getMyBookMarkDetails(
		@Parameter(hidden = true) @UserId User user
	) throws IOException {
		return ResponseModel.success(bookMarkService.getMyBookMarkDetails(user));
	}
	@Operation(summary ="북마크 등록")
	@PostMapping("")
	ResponseModel<String> addBookMark(
		@Parameter(hidden = true) @UserId User user,
		@RequestParam Long placeId,
		@RequestParam PlaceType type
	) {

		bookMarkService.addBookMark(user, placeId, type);
		return ResponseModel.success("북마크가 등록되었습니다.");
	}
	@Operation(summary ="북마크 삭제")
	@DeleteMapping("")
	ResponseModel<String> deleteBookMark(
		@Parameter(hidden = true) @UserId User user,
		@Parameter Long bookMarkId
	) {
		bookMarkService.deleteBookMark(user, bookMarkId);
		return ResponseModel.success("북마크가 삭제되었습니다.");
	}
}
