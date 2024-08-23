package com.server.booyoungee.domain.place.dto.response.hotPlace;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.booyoungee.domain.place.domain.HotPlace;
import com.server.booyoungee.domain.place.domain.Place;

import lombok.Builder;

;

@Builder
public record HotPlaceResponse(

	Long placeId,

	String type,

	String name,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	LocalDateTime updatedAt,

	int viewCount) {
	public static HotPlaceResponse from(HotPlace hotPlace) {
		Place place = hotPlace.getPlace();
		return HotPlaceResponse.builder()
			.placeId(hotPlace.getPlace().getId())
			.type(hotPlace.getType())
			.name(place.getName())
			.viewCount(place.getViewCount())
			.updatedAt(hotPlace.getUpdatedAt())
			.build();
	}

	public static HotPlaceResponse of(HotPlace hotPlace, String name) {
		Place place = hotPlace.getPlace();
		return HotPlaceResponse.builder()
			.placeId(hotPlace.getPlace().getId())
			.type(hotPlace.getType())
			.name(name)
			.viewCount(place.getViewCount())
			.updatedAt(hotPlace.getUpdatedAt())
			.build();
	}

}
