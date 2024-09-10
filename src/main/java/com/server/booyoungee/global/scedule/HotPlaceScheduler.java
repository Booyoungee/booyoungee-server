package com.server.booyoungee.global.scedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.server.booyoungee.domain.place.application.hotPlace.HotPlaceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HotPlaceScheduler {

	private final HotPlaceService hotPlaceService;

	@Scheduled(cron = "0 0 0/3 * * *") // 3 hours in milliseconds
	public void updateHotPlaces() {
		hotPlaceService.saveHotPlace();
	}

	@Scheduled(cron = "0 0 0 * * *")//24 hours in milliseconds
	public void updateViewCount() {
		//updateViewCount
	}
}