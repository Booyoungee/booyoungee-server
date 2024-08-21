package com.server.booyoungee.domain.place.application.hotPlace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.dao.hotPlace.HotPlaceRepository;
import com.server.booyoungee.domain.place.domain.HotPlace;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.domain.store.StorePlace;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceResponseDto;
import com.server.booyoungee.domain.tourInfo.application.TourInfoService;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotPlaceService {
	private final HotPlaceRepository hotPlaceRepository;
	private final TourInfoService tourInfoService;
	private final StorePlaceService storePlaceService;
	private final MoviePlaceService moviePlaceService;

	public List<HotPlaceResponseDto> getHotPlaces() {
		// Retrieve all hot places from the repository
		List<HotPlace> hotPlaces = hotPlaceRepository.findAll();

		// Transform each HotPlace entity into a HotPlaceResponseDto
		List<HotPlaceResponseDto> dto = hotPlaces.stream()
			.map(HotPlaceResponseDto::from)
			.collect(Collectors.toList());

		return dto;
	}

	@Transactional
	public void saveHotPlace() {
		hotPlaceRepository.updateAllToNotHotPlace();
		hotPlaceRepository.saveAll(getPlaceViewCount());
	}

	public List<HotPlace> getPlaceViewCount() {
		List<StorePlace> storeList = storePlaceService.Top10StorePlaces();
		List<MoviePlace> movieList = moviePlaceService.Top10MoviePlaces();
		List<TourInfoResponseDto> tourInfoList = tourInfoService.getTop10TourInfo();

		List<HotPlace> hotPlaceList = new ArrayList<>();

		hotPlaceList.addAll(storeList.stream()
			.map(place -> HotPlace.from(place.getId(), "store", place.getName(), place.getViewCount()))
			.collect(Collectors.toList()));

		hotPlaceList.addAll(movieList.stream()
			.map(place -> HotPlace.from(place.getId(), "movie", place.getName(), place.getViewCount()))
			.collect(Collectors.toList()));

		hotPlaceList.addAll(tourInfoList.stream()
			.map(place -> HotPlace.from(Long.valueOf(place.contentId()), "tour", place.title(),
				Math.toIntExact(place.views())))
			.collect(Collectors.toList()));

		// HotPlace 리스트를 viewCount 순으로 정렬
		return hotPlaceList.stream()
			.collect(Collectors.toMap(
				HotPlace::getName,  // 중복 기준: 이름
				place -> place,      // 값: HotPlace 객체
				(existing, replacement) -> existing.getViewCount() >= replacement.getViewCount() ? existing :
					replacement))  // 중복 처리: viewCount가 높은 객체 유지
			.values().stream()
			.sorted(Comparator.comparingInt(HotPlace::getViewCount).reversed())  // viewCount 기준으로 정렬
			.collect(Collectors.toList());
	}

}
