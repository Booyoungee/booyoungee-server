package com.server.booyoungee.domain.place.application.movie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.place.dao.movie.MoviePlaceLocationRepository;
import com.server.booyoungee.domain.place.domain.moviePlace.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponseDto;
import com.server.booyoungee.global.handler.ExcelSheetHandler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoviePlaceService {
	private final MoviePlaceLocationRepository moviePlaceLocationRepository;

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
				moviePlaceLocationRepository.saveAll(batchList);
				batchList.clear();
			}
		}

		if (!batchList.isEmpty()) {
			moviePlaceLocationRepository.saveAll(batchList);
		}
	}

	public MoviePlaceResponseDto getMovieLocation(Long id) {
		MoviePlace movieLocation = moviePlaceLocationRepository.findById(id).orElseThrow();
		return MoviePlaceResponseDto.builder()
			.id(movieLocation.getId())
			.movieName(movieLocation.getMovieName())
			.movieCode(movieLocation.getMovieCode())
			.name(movieLocation.getName())
			.locationAddress(movieLocation.getBasicAddress())
			.description(movieLocation.getDescription())
			.mapX(movieLocation.getMapX())
			.mapY(movieLocation.getMapY())
			.productionYear(movieLocation.getProductionYear())
			.build();
	}

	public Page<MoviePlaceResponseDto> getMovieLocationList(Pageable pageable) {
		return moviePlaceLocationRepository.findAll(pageable)
			.map(movieLocation -> MoviePlaceResponseDto.builder()
				.id(movieLocation.getId())
				.movieName(movieLocation.getMovieName())
				.movieCode(movieLocation.getMovieCode())
				.name(movieLocation.getName())
				.locationAddress(movieLocation.getBasicAddress())
				.description(movieLocation.getDescription())
				.mapX(movieLocation.getMapX())
				.mapY(movieLocation.getMapY())
				.productionYear(movieLocation.getProductionYear())
				.build()
			);
	}

	public Page<MoviePlaceResponseDto> getMovieLocationListByMovieNameKeyword(String keyword, Pageable pageable) {
		return moviePlaceLocationRepository.searchByMovieKeyword(keyword, pageable)
			.map(movieLocation -> MoviePlaceResponseDto.builder()
				.id(movieLocation.getId())
				.movieName(movieLocation.getMovieName())
				.movieCode(movieLocation.getMovieCode())
				.name(movieLocation.getName())
				.locationAddress(movieLocation.getBasicAddress())
				.description(movieLocation.getDescription())
				.mapX(movieLocation.getMapX())
				.mapY(movieLocation.getMapY())
				.productionYear(movieLocation.getProductionYear())
				.build()
			);
	}

	public Page<MoviePlaceResponseDto> getMovieLocationListByLocationKeyword(String keyword, Pageable pageable) {
		return moviePlaceLocationRepository.searchByMovieLocationKeyword(keyword, pageable)
			.map(movieLocation -> MoviePlaceResponseDto.builder()
				.id(movieLocation.getId())
				.movieName(movieLocation.getMovieName())
				.movieCode(movieLocation.getMovieCode())
				.name(movieLocation.getName())
				.locationAddress(movieLocation.getBasicAddress())
				.description(movieLocation.getDescription())
				.mapX(movieLocation.getMapX())
				.mapY(movieLocation.getMapY())
				.productionYear(movieLocation.getProductionYear())
				.build()
			);
	}
}
