package com.server.booyoungee.domain.stamp.application;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.stamp.dao.StampRepository;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.stamp.dto.StampRequestDto;
import com.server.booyoungee.domain.stamp.dto.StampResponseDto;
import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.utils.LocationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StampService {
	private final StampRepository stampRepository;

	private final TourInfoOpenApiService tourInfoOpenApiService;

	public void createStamp(User user, StampRequestDto dto) {
		double userX = dto.getUserX();
		double userY = dto.getUserY();
		double x = dto.getX();
		double y = dto.getY();
		double distance = LocationUtils.calculateDistance(userY, userX, y, x);

		if (distance <= 50) {
			if (!isExistStamp(user, dto.getPlaceId())) {
				Stamp stamp = Stamp.builder()
					.user(user)
					.placeId(dto.getPlaceId())
					.build();
				stampRepository.save(stamp);
			} else {
				throw new IllegalArgumentException("이미 존재하늩 스탬프입니다.");
			}

		} else {
			throw new IllegalArgumentException("User is not within 50 meters of the place.");
		}
	}

	public boolean isExistStamp(User user, String placeId) {
		return stampRepository.existsByUserAndPlaceId(user, placeId);
	}

	@SuppressWarnings("checkstyle:RegexpMultiline")
	public List<StampResponseDto> getStamp(User user) throws IOException {
		return stampRepository.findAllByUser(user).stream()
			.map(stamp -> {
				String placeName = null;
				try {
					placeName = tourInfoOpenApiService.getTitle(stamp.getPlaceId());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return StampResponseDto.builder()
					.stampId(stamp.getStampId())
					.createdAt(stamp.getCreatedAt())
					.updatedAt(stamp.getUpdatedAt())
					.placeId(stamp.getPlaceId())
					.placeName(placeName)
					.build();
			})
			.collect(Collectors.toList());
	}

	public StampResponseDto getStamp(User user, Long stampId) {
		Stamp stamp = stampRepository.findByUserAndStampId(user, stampId)
			.orElseThrow(() -> new IllegalArgumentException("해당 스탬프가 존재 하지 않습니다."));
		;
		String placeName = null;
		try {
			placeName = tourInfoOpenApiService.getTitle(stamp.getPlaceId());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return StampResponseDto.builder()
			.stampId(stamp.getStampId())
			.createdAt(stamp.getCreatedAt())
			.updatedAt(stamp.getUpdatedAt())
			.placeId(stamp.getPlaceId())
			.placeName(placeName)
			.build();
	}
}
