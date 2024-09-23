package com.server.booyoungee.domain.stamp.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.application.movie.MoviePlaceService;
import com.server.booyoungee.domain.place.application.tour.TourInfoOpenApiService;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceListResponse;
import com.server.booyoungee.domain.place.dto.response.movie.MoviePlaceResponse;
import com.server.booyoungee.domain.place.dto.response.tour.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.stamp.dao.StampRepository;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.stamp.dto.request.StampRequest;
import com.server.booyoungee.domain.stamp.dto.response.StampListResponse;
import com.server.booyoungee.domain.stamp.dto.response.StampPersistResponse;
import com.server.booyoungee.domain.stamp.dto.response.StampResponse;
import com.server.booyoungee.domain.stamp.exception.BadRequestStampException;
import com.server.booyoungee.domain.stamp.exception.DuplicateStampException;
import com.server.booyoungee.domain.stamp.exception.NotFoundStampException;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.utils.LocationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StampService {
	private final StampRepository stampRepository;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	private final PlaceService placeService;

	private final MoviePlaceService moviePlaceService;

	@Transactional
	public StampPersistResponse createStamp(User user, StampRequest dto) {
		double userX = Double.parseDouble(dto.userX());
		double userY = Double.parseDouble(dto.userY());
		double x = Double.parseDouble(dto.x());
		double y = Double.parseDouble(dto.y());
		double distance = LocationUtils.calculateDistance(userY, userX, y, x);

		Place place = placeService.getByPlaceId(dto.placeId());

		if (distance <= 1000) {
			if (!isExistStamp(user, place)) {
				Stamp stamp = Stamp.of(user, place, dto.type());
				return StampPersistResponse.of(stampRepository.save(stamp).getStampId());

			} else {
				throw new DuplicateStampException();
			}

		} else { //너무 멀 경우
			throw new BadRequestStampException();
		}
	}

	@Transactional
	public boolean isExistStamp(User user, Place placeId) {
		return stampRepository.existsByUserAndPlace(user, placeId);
	}

	@Transactional
	public boolean isExistStamp(User user, Long placeId) {

		Place place = placeService.getByPlaceId(placeId);
		return stampRepository.existsByUserAndPlace(user, place);
	}

	@Transactional
	public StampListResponse getStamp(User user) throws IOException {
		List<StampResponse> stampResponses = stampRepository.findAllByUser(user).stream()
			.map(stamp -> {
				try {
					return getStamp(user, stamp.getStampId());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

			})
			.collect(Collectors.toList());

		return StampListResponse.from(stampResponses);
	}

	@Transactional
	public StampResponse getStamp(User user, Long stampId) throws IOException {
		Stamp stamp = stampRepository.findByUserAndStampId(user, stampId)
			.orElseThrow(NotFoundStampException::new);
		Place place = stamp.getPlace();
		TourInfoDetailsResponseDto tourInfo = null;
		List<String> imageList = null;

		MoviePlaceResponse moviePlace = moviePlaceService.getMoviePlace(place.getId());
		imageList = placeService.moviePosterList(moviePlace);
		if (imageList.isEmpty()) {
			List<TourInfoDetailsResponseDto> tourInfoList = tourInfoOpenApiService.getTourInfoByKeyword(
				place.getName());
			tourInfo = tourInfoList != null && !tourInfoList.isEmpty() ? tourInfoList.get(0) : null;
			if (tourInfo != null) {
				imageList = placeService.placeImageList(tourInfo);
			}
		}

		return StampResponse.from(stamp, moviePlace, imageList);
	}

	@Transactional
	public StampListResponse getNearbyStamp(User user, String userX, String userY, int radius) throws IOException {

		MoviePlaceListResponse moviePlaceListResponse = moviePlaceService.getMoviePlacesNearby(userX, userY, radius);

		List<MoviePlaceResponse> moviePlaceList = moviePlaceListResponse.contents();
		List<String> imageList = null;
		List<StampResponse> stampResponses = new ArrayList<>();

		for (MoviePlaceResponse moviePlace : moviePlaceList) {
			if (!isExistStamp(user, moviePlace.id())) {
				imageList = placeService.moviePosterList(moviePlace);
				StampResponse stampResponse = StampResponse.of(moviePlace.id(), moviePlace.name(), "movie", imageList,
					moviePlace.mapX(), moviePlace.mapY());
				stampResponses.add(stampResponse);
			}
		}
		return StampListResponse.from(stampResponses);

	}

	@Transactional
	public int getStampCountByPlaceId(Long placeId) {
		Place place = placeService.getByPlaceId(placeId);
		return place.getStamps().size();
	}

	@Transactional
	public StampPersistResponse deleteStamp(User user, Long stampId) {
		Stamp stamp = stampRepository.findByUserAndStampId(user, stampId)
			.orElseThrow(NotFoundStampException::new);
		stampRepository.delete(stamp);
		return StampPersistResponse.of(stampId);
	}

}
