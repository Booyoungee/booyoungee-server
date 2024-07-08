package com.server.booyoungee.domain.tourInfo.domain.etc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TourContentType {

	TOURIST_SPOT("12", "관광지"),
	CULTURAL_FACILITY("14", "문화시설"),
	EVENT_PERFORMANCE_FESTIVAL("15", "행사/공연/축제"),
	TRAVEL_COURSE("25", "여행코스"),
	LEPORTS("28", "레포츠"),
	ACCOMMODATION("32", "숙박"),
	SHOPPING("38", "쇼핑"),
	RESTAURANT("39", "음식점");

	private final String code;
	private final String description;

	public static TourContentType fromCode(String code) {
		for (TourContentType type : values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown code: " + code);
	}

	public String getDescription() {
		return this.description;
	}
}
