package com.server.booyoungee.global.scedule;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.server.booyoungee.domain.tourInfo.dao.TourInfoRepository;
import com.server.booyoungee.domain.tourInfo.domain.TourInfo;
import com.server.booyoungee.global.redis.application.RedisService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HotPlaceScheduler {

	private final TourInfoRepository tourInfoRepository;
	private final RedisService redisService;

	@Scheduled(fixedRate = 10800000) // 3 hours in milliseconds
	public void updateHotPlacesInRedis() {
		List<TourInfo> top10TourInfos = tourInfoRepository.findTop10ByOrderByViewsDesc();

		//  redisService.saveHotPlaces(hotPlaces);
	}
}