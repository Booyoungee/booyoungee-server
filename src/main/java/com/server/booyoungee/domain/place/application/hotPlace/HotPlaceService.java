package com.server.booyoungee.domain.place.application.hotPlace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.place.PlaceService;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.dao.hotPlace.HotPlaceRepository;
import com.server.booyoungee.domain.place.domain.HotPlace;
import com.server.booyoungee.domain.place.domain.moviePlace.MoviePlace;
import com.server.booyoungee.domain.place.domain.storePlace.StorePlace;
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
	private final PlaceService placeService;
	private final StorePlaceService storePlaceService;
	private final MoviePlaceService moviePlaceService;

	public List<HotPlaceResponseDto> getHotPlaces() {
		// Retrieve all hot places from the repository
		List<HotPlace> hotPlaces = hotPlaceRepository.findAll();

		// Transform each HotPlace entity into a HotPlaceResponseDto
		List<HotPlaceResponseDto> dto = hotPlaces.stream()
			.map(hotPlace -> HotPlaceResponseDto.builder()
				.placeId(hotPlace.getPlaceId())
				.type(hotPlace.getType())
				.name(hotPlace.getName())
				.viewCount(hotPlace.getViewCount())
				.updatedAt(hotPlace.getUpdatedAt())
				.build())
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
			.map(place -> HotPlace.builder()
				.placeId(place.getId())
				.type("store")
				.name(place.getName())
				.viewCount(place.getViewCount())
				.isHotPlace(true)
				.build())
			.collect(Collectors.toList()));

		hotPlaceList.addAll(movieList.stream()
			.map(place -> HotPlace.builder()
				.placeId(place.getId())
				.type("movie")
				.name(place.getName())
				.viewCount(place.getViewCount())
				.isHotPlace(true)
				.build())
			.collect(Collectors.toList()));

		hotPlaceList.addAll(tourInfoList.stream()
			.map(place -> HotPlace.builder()
				.placeId(Long.valueOf(place.contentId()))  // Assuming place.getContentId() returns a String
				.type("tour")
				.name(place.title())
				.viewCount(Math.toIntExact(place.views()))
				.isHotPlace(true)
				.build())
			.collect(Collectors.toList()));

		// Sort the list by viewCount in descending order
		hotPlaceList.sort(Comparator.comparingInt(HotPlace::getViewCount).reversed());

		return hotPlaceList;
	}

}
