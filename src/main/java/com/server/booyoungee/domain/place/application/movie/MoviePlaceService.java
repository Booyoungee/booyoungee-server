package com.server.booyoungee.domain.place.application.movie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.like.dao.LikeRepository;
import com.server.booyoungee.domain.place.application.tour.TourInfoOpenApiService;
import com.server.booyoungee.domain.place.dao.movie.MoviePlaceRepository;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryListResponse;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryPageResponse;
import com.server.booyoungee.domain.place.dto.response.PlaceSummaryResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.exception.movie.NotFoundMoviePlaceException;
import com.server.booyoungee.domain.review.comment.dao.CommentRepository;
import com.server.booyoungee.domain.review.comment.domain.Comment;
import com.server.booyoungee.domain.review.stars.domain.Stars;
import com.server.booyoungee.global.common.PageableResponse;
import com.server.booyoungee.global.handler.ExcelSheetHandler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoviePlaceService {
	private final MoviePlaceRepository moviePlaceRepository;
	private final LikeRepository likeRepository;
	private final CommentRepository commentRepository;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	@Transactional
	public void initData() throws Exception {
		String filePath = "src/main/resources/static/movie_location_busan.xlsx";
		File file = new File(filePath);
		ExcelSheetHandler excelSheetHandler = ExcelSheetHandler.readExcel(file);
		List<List<String>> excelDatas = excelSheetHandler.getRows();

		int batchSize = 50;
		List<MoviePlace> batchList = new ArrayList<>();

		for (List<String> dataRow : excelDatas) {
			MoviePlace institution = MoviePlace.builder()
				.name(dataRow.get(0))
				.movieName(dataRow.get(1))
				.productionYear(dataRow.get(2))
				.movieCode(dataRow.get(3))
				.description(dataRow.get(4))
				.basicAddress(dataRow.get(6))
				.mapX(dataRow.get(7))
				.mapY(dataRow.get(8))
				.build();
			batchList.add(institution);
			if (batchList.size() == batchSize) {
				moviePlaceRepository.saveAll(batchList);
				batchList.clear();
			}
		}
		if (!batchList.isEmpty()) {
			moviePlaceRepository.saveAll(batchList);
		}
	}

	public MoviePlaceResponse getMoviePlace(Long id) {
		MoviePlace moviePlace = moviePlaceRepository.findById(id)
			.orElseThrow(NotFoundMoviePlaceException::new);
		moviePlace.increaseViewCount();
		moviePlaceRepository.save(moviePlace);
		return MoviePlaceResponse.from(moviePlace);
	}

	public MoviePlacePageResponse<MoviePlace> getMoviePlaces(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Long totalElements = moviePlaceRepository.count();
		List<MoviePlace> moviePlaces = moviePlaceRepository.findAllOrderByViewCount(pageable);
		List<MoviePlaceResponse> moviePlaceList = moviePlaces.stream()
			.map(MoviePlaceResponse::from)
			.collect(java.util.stream.Collectors.toList());
		return MoviePlacePageResponse.of(moviePlaceList, PageableResponse.of(pageable, totalElements));
	}

	@Transactional
	public PlaceSummaryListResponse getMoviePlacesByFilter(String filter) {
		List<Long> moviePlaceIds = new ArrayList<>();
		List<PlaceSummaryResponse> moviePlaces = new ArrayList<>();
		PlaceType type = PlaceType.movie;
		moviePlaceIds = switch (filter) {
			case "like" -> {
				List<Long> likeIds = likeRepository.findTopPlacesByLikes();
				if (likeIds == null || likeIds.isEmpty()) {
					yield moviePlaceIds;  // Default behavior if no results
				}
				yield likeIds;
			}
			case "review" -> {
				List<Long> reviewIds = commentRepository.findTopPlacesByReviews();
				if (reviewIds == null || reviewIds.isEmpty()) {
					yield moviePlaceIds;  // Default behavior if no results
				}
				yield reviewIds;
			}
			case "star" -> {
				List<Long> starIds = commentRepository.findTopPlacesByStars();
				if (starIds == null || starIds.isEmpty()) {
					yield moviePlaceIds;  // Default behavior if no results
				}
				yield starIds;
			}
			default -> moviePlaceIds;
		};

		moviePlaceIds.forEach(id -> {
			Optional<MoviePlace> moviePlace = moviePlaceRepository.findById(id);
			if (moviePlace.isPresent()) {
				moviePlaces.add(
					placeSummaryResponse(moviePlace.get())
				);
			}
		});
		return PlaceSummaryListResponse.of(moviePlaces);
	}

	public PlaceSummaryResponse placeSummaryResponse(MoviePlace moviePlace) {
		List<Stars> stars = commentRepository.findAllByPlaceId(moviePlace.getId())
			.stream()
			.map(Comment::getStars)
			.collect(Collectors.toList());
		int likeCount = likeRepository.countByPlaceId(moviePlace.getId());
		int reviewCount = commentRepository.countByPlaceId(moviePlace.getId());
		TourInfoDetailsResponseDto tourInfo = null;
		List<TourInfoDetailsResponseDto> tourInfoList = tourInfoOpenApiService.getTourInfoByKeyword(
			moviePlace.getName());
		List<String> image = new ArrayList<>();
		// Use Optional to handle potential null or empty list
		tourInfo = tourInfoList != null && !tourInfoList.isEmpty() ? tourInfoList.get(0) : null;
		if (tourInfo != null) {
			image.add(tourInfo.firstimage());
		}
		return PlaceSummaryResponse.of(moviePlace, stars, likeCount, reviewCount, PlaceType.movie, image);
	}

	public PlaceSummaryPageResponse getMoviePlacesByMovieNameKeyword(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Long totalElements = moviePlaceRepository.countByMovieNameContaining(keyword);
		List<MoviePlace> moviePlaces = moviePlaceRepository.findAllByMovieNameContainingOrderByViewCount(keyword,
			pageable);
		List<PlaceSummaryResponse> placeSummary = moviePlaces.stream()
			.map(this::placeSummaryResponse)
			.collect(Collectors.toList());
		return PlaceSummaryPageResponse.of(placeSummary, PageableResponse.of(pageable, totalElements));
	}

	public MoviePlacePageResponse<MoviePlace> getMoviePlacesByKeyword(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Long totalElements = moviePlaceRepository.countByNameContaining(keyword);
		List<MoviePlace> moviePlaces = moviePlaceRepository.findAllByNameContainingOrderByViewCount(keyword, pageable);
		List<MoviePlaceResponse> moviePlaceList = moviePlaces.stream()
			.map(MoviePlaceResponse::from)
			.collect(java.util.stream.Collectors.toList());
		return MoviePlacePageResponse.of(moviePlaceList, PageableResponse.of(pageable, totalElements));
	}

	//viewCount가 0보다 큰 영화장소를 조회하여 viewCount를 기준으로 내림차순 정렬하여 상위 10개를 조회
	public List<MoviePlace> Top10MoviePlaces() {
		return moviePlaceRepository.top10MoviePlace(PageRequest.of(0, 10));

	}

	public MoviePlaceListResponse getMoviePlacesNearby(String mapX, String mapY, int radius) {
		List<MoviePlace> moviePlaces = moviePlaceRepository.findMoviePlacesByMapXAndMapY(mapX, mapY, radius);
		return MoviePlaceListResponse.of(moviePlaces);
	}

	@Transactional
	public List<String> placeImageList(TourInfoDetailsResponseDto image) throws IOException {

		List<String> imageList = new ArrayList<>();
		if (image.firstimage() != null && !image.firstimage().equals("")) {
			imageList.add(image.firstimage());
			if (image.firstimage2() != null && !image.firstimage2().equals("")) {
				imageList.add(image.firstimage2());
			}
		}
		return imageList;
	}

}
