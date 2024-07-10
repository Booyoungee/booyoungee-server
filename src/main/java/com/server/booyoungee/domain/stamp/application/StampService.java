package com.server.booyoungee.domain.stamp.application;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.stamp.dao.StampRepository;
import com.server.booyoungee.domain.stamp.domain.Stamp;
import com.server.booyoungee.domain.stamp.dto.StampRequestDto;
import com.server.booyoungee.domain.user.application.UserService;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.utils.LocationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StampService {
	private final StampRepository stampRepository;
	private final UserService userService;

	public void createStamp(StampRequestDto dto) {
		User user = userService.findByUser(dto.getUserId());
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
}
