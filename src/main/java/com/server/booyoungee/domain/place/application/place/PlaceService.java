package com.server.booyoungee.domain.place.application.place;

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
import com.server.booyoungee.domain.place.domain.moviePlace.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsDto;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceResponse;
import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoImageDto;
import com.server.booyoungee.domain.tourInfo.dto.response.bookmark.TourInfoBookMarkDetailDto;
import com.server.booyoungee.domain.tourInfo.dto.response.bookmark.TourInfoBookMarkDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {

	private final MoviePlaceService moviePlaceService;

	private final StorePlaceService storePlaceService;

	private final TmdbApiService movieService;

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
				throw new NotFoundException("Store place with ID " + placeId + " not found.");
			}

			dto = TourInfoBookMarkDto.builder()
				.placeId(placeId + "")
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

	public PlaceDetailsDto getDetails(Long placeId, PlaceType type) throws IOException {

		PlaceDetailsDto dto;
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

			List<TourInfoImageDto> images = tourInfoOpenApiService.getTourInfoImage(moviePlace.name());
			if (!images.isEmpty()) {
				TourInfoImageDto image = images.get(0);

				if (image.firstimage() != null && !image.firstimage().equals("")) {
					imageList.add(image.firstimage());
					if (image.firstimage2() != null && !image.firstimage2().equals("")) {
						imageList.add(image.firstimage2());
					}
				}
			}

			dto = PlaceDetailsDto.builder()
				.placeId(placeId + "")
				.address(moviePlace.basicAddress())
				.images(imageList)
				.name(moviePlace.name())
				.type(type)
				.images(imageList)
				.movies(movieList)
				.posterUrl(posterUrl)
				.build();

			return dto;

		} else if (type.getKey().equals("store")) {

			StorePlaceResponse store = storePlaceService.getStore(placeId);

			List<TourInfoImageDto> images = tourInfoOpenApiService.getTourInfoImage(store.name());
			if (!images.isEmpty()) {
				TourInfoImageDto image = images.get(0);

				if (image.firstimage() != null && !image.firstimage().equals("")) {
					imageList.add(image.firstimage());
					if (image.firstimage2() != null && !image.firstimage2().equals("")) {
						imageList.add(image.firstimage2());
					}
				}
			}

			// Handle case where no images are available, leaving imageList empty

			dto = PlaceDetailsDto.builder()
				.placeId(placeId + "")
				.address(store.basicAddress())
				.name(store.name())
				.type(type)
				.images(imageList)
				.build();

			return dto;

		} else {
			TourInfoBookMarkDetailDto place = tourInfoOpenApiService.findByTourInfoDetail("" + placeId).get(0);

			if (place.firstimage() != null && !place.firstimage().isEmpty()) {
				imageList.add(place.firstimage());
				if (place.firstimage2() != null && !place.firstimage2().isEmpty()) {
					imageList.add(place.firstimage2());
				}
			}

			dto = PlaceDetailsDto.builder()
				.placeId(placeId + "")
				.address(place.addr1() + "" + place.addr2())
				.name(place.title())
				.type(type)
				.images(imageList)
				.build();
			return dto;
		}

	}

}
