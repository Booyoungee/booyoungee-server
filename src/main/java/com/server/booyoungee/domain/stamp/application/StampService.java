package com.server.booyoungee.domain.stamp.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.stamp.dao.StampRepository;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.stamp.dto.PlaceStampCountDto;
import com.server.booyoungee.domain.stamp.dto.StampResponseDto;
import com.server.booyoungee.domain.stamp.dto.request.StampRequest;
import com.server.booyoungee.domain.stamp.dto.response.StampResponse;
import com.server.booyoungee.domain.stamp.dto.response.StampResponseList;
import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.utils.LocationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StampService {
	private final StampRepository stampRepository;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	public void createStamp(User user, StampRequest dto) {
		double userX = dto.userX();
		double userY = dto.userY();
		double x = dto.x();
		double y = dto.y();
		double distance = LocationUtils.calculateDistance(userY, userX, y, x);

		if (distance <= 50) {
			if (!isExistStamp(user, dto.placeId())) {
				Stamp stamp = Stamp.builder()
					.user(user)
					.placeId(dto.placeId())
					.type(dto.type())
					.build();
				stampRepository.save(stamp);
			} else {
				throw new IllegalArgumentException("이미 존재하는 스탬프입니다.");
			}

		} else {
			throw new IllegalArgumentException("User is not within 50 meters of the place.");
		}
	}

	public boolean isExistStamp(User user, String placeId) {
		return stampRepository.existsByUserAndPlaceId(user, placeId);
	}

	public StampResponseList getStamp(User user) {
		List<Stamp> stamps = stampRepository.findAllByUser(user);
		List<StampResponse> stampResponses = stamps.stream()
			.map(stamp -> {
				String placeName = getPlaceName(stamp.getType(), stamp.getPlaceId());
				return StampResponse.of(stamp, placeName);
			})
			.toList();
		return StampResponseList.from(stampResponses);
	}

	public StampResponse getStamp(User user, Long stampId) {
		Stamp stamp = stampRepository.findByUserAndStampId(user, stampId)
			.orElseThrow(() -> new IllegalArgumentException("해당 스탬프가 존재 하지 않습니다."));
		String placeName = getPlaceName(stamp.getType(), stamp.getPlaceId());
		return StampResponse.of(stamp, placeName);
	}

	public String getPlaceName(String type, String placeId) {
		// TODO: 타입에 대한 검사 로직 필요한지 검토
		return tourInfoOpenApiService.getTitle(placeId);
	}

	public List<Stamp> getStampByPlaceId(String placeId) {
		List<Stamp> stamp = stampRepository.findByPlaceId(placeId);
		return stamp;
	}

	public int getStampCountByPlaceId(String placeId) {
		return getStampByPlaceId(placeId).size();
	}

	// TODO : 페이징 처리 제거 검토
	public Page<StampResponseDto> getPlaceStampCounts(Pageable pageable) {
		Page<PlaceStampCountDto> placeStampCounts = stampRepository.findPlaceStampCounts(pageable);
		return stampToStampResponseDto(placeStampCounts);
	}

	public Page<StampResponseDto> getPlaceStampCounts(Pageable pageable, String type) {
		Page<PlaceStampCountDto> placeStampCounts = stampRepository.findPlaceStampCountsByType(type, pageable);
		return stampToStampResponseDto(placeStampCounts);
	}

	public Page<StampResponseDto> stampToStampResponseDto(Page<PlaceStampCountDto> placeStampCounts) {
		return placeStampCounts.map(dto -> {
			try {
				String placeName = getPlaceName(dto.type(), dto.placeId());
				return StampResponseDto.builder()
					.placeId(dto.placeId())
					.placeName(placeName)
					.type(dto.type())
					.count(dto.count())
					.build();
			} catch (Exception e) {
				throw new RuntimeException("Failed to get place name", e);
			}
		});
	}

}
