package com.server.booyoungee.global.redis.application;

import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class RedisService {

        private final RedisTemplate<String, List<HotPlaceResponseDto>> redisTemplate;


        public void saveHotPlaces(List<HotPlaceResponseDto> hotPlaces) {
            redisTemplate.opsForValue().set("hot_places", hotPlaces);
        }

        public List<HotPlaceResponseDto> getHotPlaces() {
            return redisTemplate.opsForValue().get("hot_places");
        }
    }


