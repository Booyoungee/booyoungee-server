package com.server.booyoungee.domain.place.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.request.MovieImagesDto;
import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceResponse;
import com.server.booyoungee.domain.place.exception.movie.NotFoundMoviePlaceException;
import com.server.booyoungee.domain.place.exception.store.NotFoundStorePlaceException;
import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoImageDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkDetailsResponse;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {

	private final MoviePlaceService moviePlaceService;

	private final StorePlaceService storePlaceService;

	private final TmdbApiService movieService;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	public TourInfoBookMarkResponse getPlace(Long id,Long placeId, PlaceType type) throws IOException, NotFoundException {

		TourInfoBookMarkResponse dto;
		System.out.println(type);

		if (type.getKey().equals("movie")) {

			MoviePlaceResponse movie = moviePlaceService.getMoviePlace(placeId);
			if (movie == null) {
				throw new NotFoundMoviePlaceException();
			}

			dto = TourInfoBookMarkResponse.builder().bookmarkId(id)
				.placeId(placeId + "")
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
				throw new NotFoundStorePlaceException();
			}

			dto = TourInfoBookMarkResponse.builder().bookmarkId(id)
				.placeId(placeId + "")
				.title(store.name())
				.placeType("store")
				.mapx(store.mapX()) // Uncomment if mapX is available in store response
				.mapy(store.mapY()) // Uncomment if mapY is available in store response
				.contentid(store.id().toString())
				.build();

			return dto;

		} else {
			List<TourInfoBookMarkResponse> tourInfo = tourInfoOpenApiService.findByContentId(placeId.toString());
			if (tourInfo == null || tourInfo.isEmpty()) {
				throw new NotFoundException("Tour place with ID " + placeId + " not found.");
			}
			TourInfoBookMarkResponse tour = tourInfo.get(0);

			dto = TourInfoBookMarkResponse.builder().bookmarkId(id)
					.placeId(placeId + "")
					.title(tour.title())
					.placeType("store")
					.mapx(tour.mapx()) // Uncomment if mapX is available in store response
					.mapy(tour.mapy()) // Uncomment if mapY is available in store response
					.contentid(tour.contentid())
					.build();
			return dto;
		}
	}
	public List<String> placeImageList(String name) throws IOException {
		List<TourInfoImageDto> images = tourInfoOpenApiService.getTourInfoImage(name);
		List<String> imageList = new ArrayList<>();
		if (!images.isEmpty()) {
			TourInfoImageDto image = images.get(0);

			if (image.firstimage() != null && !image.firstimage().equals("")) {
				imageList.add(image.firstimage());
				if (image.firstimage2() != null && !image.firstimage2().equals("")) {
					imageList.add(image.firstimage2());
				}
			}
		}
		return imageList;
	}


	public PlaceDetailsResponse getDetails(Long placeId, PlaceType type) throws IOException {

		PlaceDetailsResponse dto;
		List<String> imageList = new ArrayList<>();

		if (type.getKey().equals("movie")) {

			MoviePlaceResponse moviePlace = moviePlaceService.getMoviePlace(placeId);

			MoviePlacePageResponse<MoviePlace> places = moviePlaceService.getMoviePlacesByKeyword(moviePlace.name(), 1,
				3);

			List<String> posterUrl = new ArrayList<>();
			List<String> movieList = new ArrayList<>();
			for (MoviePlaceResponse movieTag : places.contents()) {
				movieList.add(movieTag.movieName());
			}
			if (movieList.isEmpty()) {
				movieList.add(moviePlace.movieName());
			}

			List<MovieImagesDto.BackDrops> backdrops = movieService.getMovie(moviePlace.movieName()).getBackdrops();
			for (MovieImagesDto.BackDrops backdrop : backdrops) {
				posterUrl.add(backdrop.getFilePath());
			}
			dto = PlaceDetailsResponse.of(placeId + "", moviePlace.name(), moviePlace.basicAddress(), placeImageList(moviePlace.name()), type, movieList, posterUrl);

			return dto;

		} else if (type.getKey().equals("store")) {

			StorePlaceResponse store = storePlaceService.getStore(placeId);
			dto = PlaceDetailsResponse.of(placeId + "", store.name(), store.basicAddress(), imageList, type, null, null);

			return dto;
		} else {
			TourInfoBookMarkDetailsResponse place = tourInfoOpenApiService.findByTourInfoDetail("" + placeId).get(0);

			if (place.firstimage() != null && !place.firstimage().isEmpty()) {
				imageList.add(place.firstimage());
				if (place.firstimage2() != null && !place.firstimage2().isEmpty()) {
					imageList.add(place.firstimage2());
				}
			}

			dto = PlaceDetailsResponse.of(placeId + "", place.title(), place.addr1(), imageList, type, null, null);
			return dto;
		}

	}

}
