package com.server.booyoungee.domain.stamp.application;

import static com.server.booyoungee.domain.stamp.exception.StampExceptionCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.booyoungee.domain.place.application.PlaceService;
import com.server.booyoungee.domain.place.application.tour.TourInfoOpenApiService;
import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.stamp.dao.StampRepository;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.stamp.dto.request.StampRequest;
import com.server.booyoungee.domain.stamp.dto.response.StampPersistResponse;
import com.server.booyoungee.domain.stamp.dto.response.StampResponse;
import com.server.booyoungee.domain.stamp.dto.response.StampResponseList;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.utils.LocationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StampService {
	private final StampRepository stampRepository;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	private final PlaceService placeService;

	@Transactional
	public StampPersistResponse createStamp(User user, StampRequest dto) {
		double userX = dto.userX();
		double userY = dto.userY();
		double x = dto.x();
		double y = dto.y();
		double distance = LocationUtils.calculateDistance(userY, userX, y, x);

		Place place = placeService.getByPlaceId(dto.placeId(), dto.type());

		if (distance <= 50) {
			if (!isExistStamp(user, place)) {
				Stamp stamp = Stamp.of(user, place, dto.type());
				return StampPersistResponse.of(stampRepository.save(stamp).getStampId());

			} else {
				throw new CustomException(DUPLICATE_STAMP);
			}

		} else {
			throw new CustomException(SO_FAR);
		}
	}

	public boolean isExistStamp(User user, Place placeId) {
		return stampRepository.existsByUserAndPlaceId(user, placeId);
	}

	public StampResponseList getStamp(User user) {
		List<Stamp> stamps = stampRepository.findAllByUser(user);
		List<StampResponse> stampResponses = stamps.stream()
			.map(stamp -> {
				return StampResponse.from(stamp);
			})
			.toList();
		return StampResponseList.from(stampResponses);
	}

	public StampResponse getStamp(User user, Long stampId) {
		Stamp stamp = stampRepository.findByUserAndStampId(user, stampId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_STAMP));
		return StampResponse.from(stamp);
	}

	@Transactional
	public int getStampCountByPlaceId(Long placeId) {
		Place place = placeService.getByPlaceId(placeId);
		return place.getStamps().size();
	}

	public StampPersistResponse deleteStamp(User user, Long stampId) {
		Stamp stamp = stampRepository.findByUserAndStampId(user, stampId)
			.orElseThrow(() -> new CustomException(NOT_FOUND_STAMP));
		stampRepository.delete(stamp);
		return StampPersistResponse.of(stampId);
	}
}
