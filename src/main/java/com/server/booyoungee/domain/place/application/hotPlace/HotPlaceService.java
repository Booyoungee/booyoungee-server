package com.server.booyoungee.domain.place.application.hotPlace;

import static com.server.booyoungee.domain.place.exception.tour.TourInfoExceptionCode.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.application.tour.TourPlaceService;
import com.server.booyoungee.domain.place.dao.hotPlace.HotPlaceRepository;
import com.server.booyoungee.domain.place.domain.HotPlace;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlacePersistReponse;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.global.exception.CustomException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotPlaceService {
	private final HotPlaceRepository hotPlaceRepository;
	private final TourPlaceService tourInfoService;
	private final StorePlaceService storePlaceService;
	private final MoviePlaceService moviePlaceService;
	private final PlaceService placeService;

	public HotPlaceListResponse getHotPlaces() {
		// Retrieve all hot places from the repository
		List<HotPlace> hotPlaces = hotPlaceRepository.findAll();

		List<HotPlaceResponse> dto = hotPlaces.stream()
			.map(hotPlace -> {
				String name = hotPlace.getPlace().getName();
				if ("tour".equals(hotPlace.getType())) {
					try {
						TourInfoDetailsResponseDto tourPlace = tourInfoService.getTour(hotPlace.getPlace().getId());
						name = tourPlace.title();
						System.out.println(name);
					} catch (Exception e) {
						// Handle any exceptions that might occur during the API call
						throw new CustomException(NOT_FOUND_TOUR_PLACE);
					}
				}

				return HotPlaceResponse.of(hotPlace, name);
			})
			.collect(Collectors.toList());
		HotPlaceListResponse response = HotPlaceListResponse.of(dto);
		return response;
	}

	@Transactional
	public HotPlacePersistReponse saveHotPlace() {

		hotPlaceRepository.updateAllToNotHotPlace();
		return HotPlacePersistReponse.from(saveAllHotPlace(getPlaceViewCount()));
	}

	public List<HotPlace> saveAllHotPlace(List<HotPlace> hotPlaces) {
		return hotPlaceRepository.saveAll(hotPlaces);
	}

	public List<HotPlace> getPlaceViewCount() {

		List<Place> placeList = placeService.getTop10Place().getContent();
		Map<String, HotPlace> hotPlaceMap = new HashMap<>();

		placeList.forEach(place -> {
			HotPlace hotPlace = HotPlace.from(place);
			hotPlaceMap.merge(hotPlace.getPlace().getName(), hotPlace, (existing, newPlace) ->
				existing.getPlace().getViewCount() >= newPlace.getPlace().getViewCount() ? existing : newPlace);
		});

		// Convert the map values to a list and sort by viewCount in descending order
		return hotPlaceMap.values().stream()
			.sorted(Comparator.comparingInt(hotPlace -> -hotPlace.getPlace().getViewCount()))
			.collect(Collectors.toList());

	}

}
