package com.server.booyoungee.global.redis.application;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlaceResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, Object> redisTemplate;

	public void saveHotPlaces(List<HotPlaceResponseDto> hotPlaces) {
		redisTemplate.opsForValue().set("hot_places", hotPlaces);
	}

	public List<HotPlaceResponseDto> getHotPlaces() {
		return (List<HotPlaceResponseDto>)redisTemplate.opsForValue().get("hot_places");
	}
}


