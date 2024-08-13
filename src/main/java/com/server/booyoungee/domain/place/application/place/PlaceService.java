package com.server.booyoungee.domain.place.application.place;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceResponse;
import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {

	private final MoviePlaceService moviePlaceService;

	private final StorePlaceService storePlaceService;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	public TourInfoBookMarkDto getPlace(Long placeId, PlaceType type) throws IOException, NotFoundException {

		TourInfoBookMarkDto dto;
		System.out.println(type);

		if (type.getKey().equals("movie")) {

			MoviePlaceResponse movie = moviePlaceService.getMoviePlace(placeId);
			if (movie == null) {
				throw new NotFoundException("Movie place with ID " + placeId + " not found.");
			}

			dto = TourInfoBookMarkDto.builder()
				.title(movie.name())
				.placeType("movie")
				.mapx(movie.mapX())
				.mapy(movie.mapY())
				.contentid(movie.id().toString())
				.build();

			return dto;

		} else if (type.getKey().equals("store")) {

			StorePlaceResponse store = storePlaceService.getStore(placeId);
			if (store == null) {
				throw new NotFoundException("Store place with ID " + placeId + " not found.");
			}

			dto = TourInfoBookMarkDto.builder()
				.title(store.name())
				.placeType("store")
				.mapx(store.mapX()) // Uncomment if mapX is available in store response
				.mapy(store.mapY()) // Uncomment if mapY is available in store response
				.contentid(store.id().toString())
				.build();

			return dto;

		} else {
			List<TourInfoBookMarkDto> tourInfo = tourInfoOpenApiService.findByContentId(placeId.toString());
			if (tourInfo == null || tourInfo.isEmpty()) {
				throw new NotFoundException("Tour place with ID " + placeId + " not found.");
			}
			return tourInfo.get(0);
		}
	}

}
