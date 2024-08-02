package com.server.booyoungee.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlaceType {

	STORE("STORE", "지역 상생 식당"),
	MOVIE("MOVIE", "영화 촬영지 장소")
	;

	private final String key;
	private final String description;
}
