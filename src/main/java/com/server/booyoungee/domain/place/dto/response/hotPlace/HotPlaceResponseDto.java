package com.server.booyoungee.domain.place.dto.response.hotPlace;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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

}
