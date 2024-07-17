package com.server.booyoungee.domain.movieLocation.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.movieLocation.dao.MovieLocationRepository;
import com.server.booyoungee.domain.movieLocation.domain.MovieLocation;
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
}
