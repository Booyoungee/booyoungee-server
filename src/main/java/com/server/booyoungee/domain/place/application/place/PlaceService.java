package com.server.booyoungee.domain.place.application.place;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {

	private final MoviePlaceService moviePlaceService;

	private final StorePlaceService storePlaceService;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	public Object getPlace(Long placeId, PlaceType type) throws IOException {
		if (type.getKey().equals("movie")) {
			return moviePlaceService.getMoviePlace(placeId);
		} else if (type.getKey().equals("store")) {
			return storePlaceService.getStore(placeId);
		} else {
			return tourInfoOpenApiService.findByContentId(placeId.toString());
		}
	}
}
