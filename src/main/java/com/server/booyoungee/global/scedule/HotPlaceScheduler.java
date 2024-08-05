package com.server.booyoungee.global.scedule;

import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceResponseDto;
import com.server.booyoungee.domain.tourInfo.dao.TourInfoRepository;
import com.server.booyoungee.domain.tourInfo.domain.TourInfo;
import com.server.booyoungee.global.redis.application.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HotPlaceScheduler {

    private final TourInfoRepository tourInfoRepository;
    private final RedisService redisService;


    @Scheduled(fixedRate = 10800000) // 3 hours in milliseconds
    public void updateHotPlacesInRedis() {
        List<TourInfo> top10TourInfos = tourInfoRepository.findTop10ByOrderByViewsDesc();
        List<HotPlaceResponseDto> hotPlaces = top10TourInfos.stream()
                .map(tourInfo -> new HotPlaceResponseDto(
                        tourInfo.getContentId(),
                        tourInfo.getContentTypeId().toString()
                       // tourInfo.getName(),  // assuming getName() exists
                       // tourInfo.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        redisService.saveHotPlaces(hotPlaces);
    }
}