package com.server.booyoungee.global.scedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.server.booyoungee.domain.place.application.hotPlace.HotPlaceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HotPlaceScheduler {

	private final HotPlaceService hotPlaceService;

	@Scheduled(fixedRate = 10800000) // 3 hours in milliseconds
	public void updateHotPlaces() {
		hotPlaceService.saveHotPlace();
	}

	@Scheduled(fixedRate = 10800000 * 8)//24 hours in milliseconds
	public void updateViewCount() {
		//updateViewCount
	}
}