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
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkDetailsResponse;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkResponse;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoImageDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {

	private final MoviePlaceService moviePlaceService;

	private final StorePlaceService storePlaceService;

	private final TmdbApiService movieService;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	public TourInfoBookMarkResponse getPlace(Long id, Long placeId, PlaceType type) throws
		IOException,
		NotFoundException {

		if (type.getKey().equals("movie")) {
			MoviePlaceResponse movie = moviePlaceService.getMoviePlace(placeId);
			if (movie == null) {
				throw new NotFoundMoviePlaceException();
			}
			return TourInfoBookMarkResponse.fromPlace(movie.id().toString(), movie.name(), movie.mapX(), movie.mapY(),
				"", "movie");

		} else if (type.getKey().equals("store")) {
			StorePlaceResponse store = storePlaceService.getStore(placeId);
			if (store == null) {
				throw new NotFoundStorePlaceException();
			}
			return TourInfoBookMarkResponse.fromPlace(store.id().toString(), store.name(), store.mapX(), store.mapY(),
				"", "store");

		} else {
			List<TourInfoBookMarkResponse> tourInfoList = tourInfoOpenApiService.findByContentId(placeId.toString());
			if (tourInfoList == null || tourInfoList.isEmpty()) {
				throw new NotFoundException("Tour place with ID " + placeId + " not found.");
			}
			TourInfoBookMarkResponse tourInfo = tourInfoList.get(0);
			return TourInfoBookMarkResponse.fromPlace(tourInfo.placeId(), tourInfo.title(), tourInfo.mapx(),
				tourInfo.mapy(), tourInfo.contentid(), "tour");
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
			dto = PlaceDetailsResponse.of(placeId + "", moviePlace.name(), moviePlace.basicAddress(),
				placeImageList(moviePlace.name()), type, movieList, posterUrl);

			return dto;

		} else if (type.getKey().equals("store")) {

			StorePlaceResponse store = storePlaceService.getStore(placeId);
			dto = PlaceDetailsResponse.of(placeId + "", store.name(), store.basicAddress(), imageList, type, null,
				null);

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
