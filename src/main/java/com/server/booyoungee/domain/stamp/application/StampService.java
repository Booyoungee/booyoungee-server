package com.server.booyoungee.domain.stamp.application;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.stamp.dao.StampRepository;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.stamp.dto.PlaceStampCountDto;
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
					.type(dto.getType())
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
					getPlaceName(stamp.getType(), stamp.getPlaceId());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return StampResponseDto.builder()
					.stampId(stamp.getStampId())
					.createdAt(stamp.getCreatedAt())
					.updatedAt(stamp.getUpdatedAt())
					.placeId(stamp.getPlaceId())
					.type(stamp.getType())
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
			placeName = getPlaceName(stamp.getType(), stamp.getPlaceId());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return StampResponseDto.builder()
			.stampId(stamp.getStampId())
			.createdAt(stamp.getCreatedAt())
			.updatedAt(stamp.getUpdatedAt())
			.placeId(stamp.getPlaceId())
			.type(stamp.getType())
			.placeName(placeName)
			.build();
	}

	public String getPlaceName(String type, String placeId) throws IOException {
		String name = null;
		if (type == "movie") {

		} else if (type == "100") {

		} else {
			name = tourInfoOpenApiService.getTitle(placeId);
		}
		return name;
	}

	public List<Stamp> getStampByPlaceId(String placeId) {
		List<Stamp> stamp = stampRepository.findByPlaceId(placeId);
		return stamp;
	}

	public int getStampCountByPlaceId(String placeId) {
		return getStampByPlaceId(placeId).size();
	}

	public List<StampResponseDto> getPlaceStampCounts() throws IOException {
		List<PlaceStampCountDto> placeStampCounts = stampRepository.findPlaceStampCounts();

		return placeStampCounts.stream()
			.map(dto -> {
				try {
					String placeName = getPlaceName(dto.getType(), dto.getPlaceId());
					return StampResponseDto.builder()
						.placeId(dto.getPlaceId())
						.placeName(placeName)
						.type(dto.getType())
						.count(dto.getCount())
						.build();
				} catch (IOException e) {
					throw new RuntimeException("Failed to get place name", e);
				}
			})
			.collect(Collectors.toList());
	}

}
