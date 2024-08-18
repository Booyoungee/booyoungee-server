package com.server.booyoungee.domain.place.dto.response.hotPlace;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.booyoungee.domain.place.domain.HotPlace;

import lombok.Builder;

;

@Builder
public record HotPlaceResponseDto(

	Long placeId,

	String type,

	String name,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	LocalDateTime updatedAt,

	int viewCount) {
	public static HotPlaceResponseDto from(HotPlace hotPlace) {
		return HotPlaceResponseDto.builder()
			.placeId(hotPlace.getPlaceId())
			.type(hotPlace.getType())
			.name(hotPlace.getName())
			.viewCount(hotPlace.getViewCount())
			.updatedAt(hotPlace.getUpdatedAt())
			.build();
	}

}
