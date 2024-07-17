package com.server.booyoungee.domain.movieLocation.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.movieLocation.dao.MovieLocationRepository;
import com.server.booyoungee.domain.movieLocation.domain.MovieLocation;
import com.server.booyoungee.domain.movieLocation.dto.response.MovieLocationResponseDto;
import com.server.booyoungee.global.handler.ExcelSheetHandler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieLocationService {
	private final MovieLocationRepository movieLocationRepository;

	@Transactional
	public void initData() throws Exception {
		String filePath = "src/main/resources/static/movie_location_busan.xlsx";
		File file = new File(filePath);
		ExcelSheetHandler excelSheetHandler = ExcelSheetHandler.readExcel(file);
		List<List<String>> excelDatas = excelSheetHandler.getRows();

		int batchSize = 50;
		List<MovieLocation> batchList = new ArrayList<>();

		for (List<String> dataRow : excelDatas) {
			MovieLocation institution = MovieLocation.builder()
				.locationName(dataRow.get(0))
				.movieName(dataRow.get(1))
				.productedAt(dataRow.get(2))
				.movieCode(dataRow.get(3))
				.description(dataRow.get(4))
				.area(dataRow.get(5))
				.locationAddress(dataRow.get(6))
				.mapx(dataRow.get(7))
				.mapy(dataRow.get(8))
				.build();
			batchList.add(institution);

			if (batchList.size() == batchSize) {
				movieLocationRepository.saveAll(batchList);
				batchList.clear();
			}
		}

		if (!batchList.isEmpty()) {
			movieLocationRepository.saveAll(batchList);
		}
	}

	public MovieLocationResponseDto getMovieLocation(Long id) {
		MovieLocation movieLocation = movieLocationRepository.findById(id).orElseThrow();
		return MovieLocationResponseDto.builder()
			.id(movieLocation.getId())
			.movieName(movieLocation.getMovieName())
			.movieCode(movieLocation.getMovieCode())
			.locationName(movieLocation.getLocationName())
			.locationAddress(movieLocation.getLocationAddress())
			.description(movieLocation.getDescription())
			.area(movieLocation.getArea())
			.mapx(movieLocation.getMapx())
			.mapy(movieLocation.getMapy())
			.productedAt(movieLocation.getProductedAt())
			.build();
	}

	public Page<MovieLocationResponseDto> getMovieLocationList(Pageable pageable) {
		return movieLocationRepository.findAll(pageable)
			.map(movieLocation -> MovieLocationResponseDto.builder()
				.id(movieLocation.getId())
				.movieName(movieLocation.getMovieName())
				.movieCode(movieLocation.getMovieCode())
				.locationName(movieLocation.getLocationName())
				.locationAddress(movieLocation.getLocationAddress())
				.description(movieLocation.getDescription())
				.area(movieLocation.getArea())
				.mapx(movieLocation.getMapx())
				.mapy(movieLocation.getMapy())
				.productedAt(movieLocation.getProductedAt())
				.build()
			);
	}

	public Page<MovieLocationResponseDto> getMovieLocationListByMovieNameKeyword(String keyword, Pageable pageable) {
		return movieLocationRepository.searchByMovieKeyword(keyword, pageable)
			.map(movieLocation -> MovieLocationResponseDto.builder()
				.id(movieLocation.getId())
				.movieName(movieLocation.getMovieName())
				.movieCode(movieLocation.getMovieCode())
				.locationName(movieLocation.getLocationName())
				.locationAddress(movieLocation.getLocationAddress())
				.description(movieLocation.getDescription())
				.area(movieLocation.getArea())
				.mapx(movieLocation.getMapx())
				.mapy(movieLocation.getMapy())
				.productedAt(movieLocation.getProductedAt())
				.build()
			);
	}

	public Page<MovieLocationResponseDto> getMovieLocationListByLocationKeyword(String keyword, Pageable pageable){
		return movieLocationRepository.searchByMovieLocationKeyword(keyword, pageable)
				.map(movieLocation -> MovieLocationResponseDto.builder()
						.id(movieLocation.getId())
						.movieName(movieLocation.getMovieName())
						.movieCode(movieLocation.getMovieCode())
						.locationName(movieLocation.getLocationName())
						.locationAddress(movieLocation.getLocationAddress())
						.description(movieLocation.getDescription())
						.area(movieLocation.getArea())
						.mapx(movieLocation.getMapx())
						.mapy(movieLocation.getMapy())
						.productedAt(movieLocation.getProductedAt())
						.build()
				);
	}
}
