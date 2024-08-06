package com.server.booyoungee.domain.BookMark.api;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.BookMark.application.BookMarkService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.domain.user.interceptor.UserId;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookMarkController {

	private final BookMarkService bookMarkService;

	@GetMapping("")
	public ResponseModel<?> getBookMarks(
		@Parameter(hidden = true) @UserId User user) throws IOException {
		return ResponseModel.success(bookMarkService.getBookMarks(user));
	}

	@GetMapping("/me")
	ResponseModel<?> getMyBookMarkDetails(
		@Parameter(hidden = true) @UserId User user) throws IOException {
		return ResponseModel.success(bookMarkService.getMyBookMarkDetails(user));
	}

	@PostMapping("")
	ResponseModel<?> addBookMark(
		@Parameter(hidden = true) @UserId User user,
		@Parameter Long placeId,
		@Parameter PlaceType type) throws IOException {
		bookMarkService.addBookMark(user, placeId, type);
		return ResponseModel.success("북마크가 생성되었습니다.");
	}

	@GetMapping("/test")
	ResponseModel<?> getMyBookMarkDetails(@Parameter Long placeId,
		@Parameter PlaceType type) throws IOException {

		return ResponseModel.success(bookMarkService.getPlace(placeId, type));
	}

}
