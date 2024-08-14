package com.server.booyoungee.domain.movie.api;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.response.MovieDetailsDto;
import com.server.booyoungee.global.common.ResponseModel;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
@Tag(name = "Movie", description = "영화 정보 api / 담당자 : 이영학")
public class MovieController {

	private final TmdbApiService tmdbApiService;
	@Operation(summary = "영화 제목으로 backdrop 이미지 조회",description = "영화 제목을 입력하면 해당 영화의 backdrop 이미지를 조회합니다. \n+" +
			"한 번 조회한 영화는 DB에 저장되어 다음 조회 시 DB에서 조회합니다. \n" )
	@GetMapping("")
	public ResponseModel<MovieDetailsDto> getMovie(
		@RequestParam String title
	) {
		MovieDetailsDto movie = tmdbApiService.getMovie(title);
		return ResponseModel.success(movie);
	}
	@Hidden
	@PostMapping("")
	public ResponseModel<?> saveMovieImages(@RequestParam String title) {
		tmdbApiService.saveMovie(title);
		return ResponseModel.success(true);
	}

}
