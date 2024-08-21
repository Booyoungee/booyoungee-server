package com.server.booyoungee.domain.place.application.movie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.place.dao.movie.MoviePlaceRepository;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.exception.movie.NotFoundMoviePlaceException;
import com.server.booyoungee.global.common.PageableResponse;
import com.server.booyoungee.global.handler.ExcelSheetHandler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoviePlaceService {
	private final MoviePlaceRepository moviePlaceRepository;

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

	public MoviePlacePageResponse<MoviePlace> getMoviePlacesByMovieNameKeyword(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Long totalElements = moviePlaceRepository.countByMovieNameContaining(keyword);
		List<MoviePlace> moviePlaces = moviePlaceRepository.findAllByMovieNameContainingOrderByViewCount(keyword,
			pageable);
		List<MoviePlaceResponse> moviePlaceList = moviePlaces.stream()
			.map(MoviePlaceResponse::from)
			.collect(java.util.stream.Collectors.toList());
		return MoviePlacePageResponse.of(moviePlaceList, PageableResponse.of(pageable, totalElements));
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

	public MoviePlacePageResponse<MoviePlace> findPlacesNearby(String mapX, String mapY, String radius, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		double longtitude = Double.parseDouble(mapX);
		double latitude = Double.parseDouble(mapY);
		double rad = Double.parseDouble(radius);
		Long totalElements = moviePlaceRepository.countNearbyMoviePlaces(latitude, longtitude, rad);
		List<MoviePlace> moviePlaces = moviePlaceRepository.findNearbyMoviePlaces(latitude, longtitude, rad,pageable);

		List<MoviePlaceResponse> moviePlaceList = moviePlaces.stream()
				.map(MoviePlaceResponse::from)
				.collect(java.util.stream.Collectors.toList());
		return MoviePlacePageResponse.of(moviePlaceList, PageableResponse.of(pageable, totalElements));
	}
}
