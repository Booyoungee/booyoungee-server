package com.server.booyoungee.domain.place.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import com.server.booyoungee.domain.bookmark.dto.response.BookMarkResponse;
import com.server.booyoungee.domain.movie.application.TmdbApiService;
import com.server.booyoungee.domain.movie.dto.request.MovieImagesDto;
import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.store.StorePlaceService;
import com.server.booyoungee.domain.place.application.tour.TourInfoOpenApiService;
import com.server.booyoungee.domain.place.application.tour.TourPlaceService;
import com.server.booyoungee.domain.place.dao.PlaceRepository;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlacePageResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.store.StorePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoImageDto;
import com.server.booyoungee.domain.place.exception.NotFoundPlaceException;
import com.server.booyoungee.domain.place.exception.movie.NotFoundMoviePlaceException;
import com.server.booyoungee.domain.place.exception.store.NotFoundStorePlaceException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {
	private final TourPlaceService placeService;
	private final PlaceRepository placeRepository;
	private final MoviePlaceService moviePlaceService;
	private final StorePlaceService storePlaceService;
	private final TmdbApiService movieService;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	@Transactional
	public BookMarkResponse getPlace(Long id, Long placeId, PlaceType type) throws
		IOException,
		NotFoundException {

		if (type.getKey().equals("movie")) {
			MoviePlaceResponse movie = moviePlaceService.getMoviePlace(placeId);
			if (movie == null) {
				throw new NotFoundMoviePlaceException();
			}
			return BookMarkResponse.of(id, placeId, null, movie.name(), movie.mapX(), movie.mapY(),
				type, type.getKey());

		} else if (type.getKey().equals("store")) {
			StorePlaceResponse store = storePlaceService.getStore(placeId);
			if (store == null) {
				throw new NotFoundStorePlaceException();
			}
			return BookMarkResponse.of(id, placeId, null, store.name(), store.mapX(), store.mapY(),
				type, type.getKey());

		} else {

			TourInfoDetailsResponseDto tourInfo = placeService.getTour(placeId);
			return BookMarkResponse.of(id, placeId, tourInfo.contentid(), tourInfo.title(), tourInfo.mapx(),
				tourInfo.mapy(),
				type, tourInfo.contenttypeid());
		}
	}

	@Transactional
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

	@Transactional
	public PlaceDetailsResponse getDetails(Long placeId, PlaceType type) throws IOException {

		PlaceDetailsResponse dto;
		List<String> imageList = new ArrayList<>();

		Place place = getByPlaceId(placeId);

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
				placeImageList(moviePlace.name()), type, movieList, posterUrl, 0, 0);

			return dto;

		} else if (type.getKey().equals("store")) {

			StorePlaceResponse store = storePlaceService.getStore(placeId);
			dto = PlaceDetailsResponse.of(placeId + "", store.name(), store.basicAddress(), imageList, type, null,
				null, place.getLikes().size(), 0);

			return dto;

		} else {
			TourInfoDetailsResponseDto tourPlace = placeService.getTour(placeId);

			if (tourPlace.firstimage() != null && !tourPlace.firstimage().isEmpty()) {
				imageList.add(tourPlace.firstimage());
				if (tourPlace.firstimage2() != null && !tourPlace.firstimage2().isEmpty()) {
					imageList.add(tourPlace.firstimage2());
				}
			}

			dto = PlaceDetailsResponse.of(placeId + "", tourPlace.title(), tourPlace.addr1(), imageList, type, null,
				null, 0,
				0);
			return dto;
		}

	}

	//조회할 때 place 가져올 때
	public Place getByPlaceId(Long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(NotFoundPlaceException::new);
	}

	//등록할 때 place 가져올 때
	public Place getByPlaceId(Long placeId, String type) {
		if (type.equals("tour")) {
			Place place = placeRepository.findById(placeId)
				.orElse(null);
			if (place == null) {
				place = placeService.saveTourPlace(placeId);
				return place;
			}
			return place;

		} else {
			Place place = placeRepository.findById(placeId)
				.orElseThrow(NotFoundPlaceException::new);
			return place;
		}
	}

	public Page<Place> getTop10Place() {
		Pageable pageable = PageRequest.of(0, 10);
		return placeRepository.findTop10ByOrderByViewCountDesc(pageable);
	}
}
