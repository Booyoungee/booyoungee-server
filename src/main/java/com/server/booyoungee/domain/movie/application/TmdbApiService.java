package com.server.booyoungee.domain.movie.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.server.booyoungee.domain.movie.dao.MovieRepository;
import com.server.booyoungee.domain.movie.domain.Movie;
import com.server.booyoungee.domain.movie.dto.request.MovieImagesDto;
import com.server.booyoungee.domain.movie.dto.request.MoviePosterDto;
import com.server.booyoungee.domain.movie.dto.response.MovieDetailsDto;
import com.server.booyoungee.domain.movie.dto.response.TmdbResponseDto;
import com.server.booyoungee.domain.movie.exception.NotFoundMovieInfoException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TmdbApiService {
	@Value("${movies.api.key}")
	private String apiKey;
	private String baseUrl = "https://api.themoviedb.org/3";

	private final RestTemplate restTemplate;
	private final MovieRepository movieRepository;

	public TmdbResponseDto searchMoviePosterList(String query) {
		String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search/movie")
			.queryParam("api_key", apiKey)
			.queryParam("query", query)
			.queryParam("language", "ko")
			.queryParam("page", 1)
			.queryParam("region", "ko")
			.toUriString();
		return restTemplate.getForObject(url, TmdbResponseDto.class);
	}

	public MovieImagesDto searchMovieImages(String id) {
		String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
			.path("/movie/{id}/images")
			.queryParam("api_key", apiKey)
			.queryParam("language", "ko-KR")
			.queryParam("include_image_language", "ko-KR,null,en")
			.buildAndExpand(id)
			.toUriString();
		return restTemplate.getForObject(url, MovieImagesDto.class);
	}

	public void saveMovie(String title) {
		TmdbResponseDto response = searchMoviePosterList(title);
		if (response != null && !response.getResults().isEmpty()) {
			MoviePosterDto moviePosterDto = response.getResults().get(0);
			MovieImagesDto movieImagesDto = searchMovieImages(moviePosterDto.getId());

			String backdrops = movieImagesDto.getBackdrops().stream()
				.limit(4)
				.map(MovieImagesDto.BackDrops::getFilePath)
				.collect(Collectors.joining("|"));

			Movie movie = Movie.builder()
				.dto(moviePosterDto)
				.backdrops(backdrops)
				.build();

			movieRepository.save(movie);
		}
	}

	public MovieDetailsDto getMovie(String title) {
		Optional<Movie> movieOptional = movieRepository.findByTitle(title);
		if (movieOptional.isEmpty()) {
			saveMovie(title);
			movieOptional = movieRepository.findByTitle(title);
		}

		return movieOptional.map(this::mapToMovieDetailsDto)
			.orElseThrow(NotFoundMovieInfoException::new);
	}

	private MovieDetailsDto mapToMovieDetailsDto(Movie movie) {
		List<MovieImagesDto.BackDrops> backDropsList = getBackDropList(movie);
		return MovieDetailsDto.builder()
			.id(movie.getMovieid())
			.title(movie.getTitle())
			.posterPath(movie.getPosterPath())
			.backDrops(backDropsList)
			.build();
	}

	private List<MovieImagesDto.BackDrops> getBackDropList(Movie movie) {
		return List.of(movie.getBackdrops().split("\\|")).stream()
			.map(path -> {
				MovieImagesDto.BackDrops backDrops = new MovieImagesDto.BackDrops();
				backDrops.setFilePath("https://image.tmdb.org/t/p/w500" + path);
				return backDrops;
			})
			.collect(Collectors.toList());
	}
}
