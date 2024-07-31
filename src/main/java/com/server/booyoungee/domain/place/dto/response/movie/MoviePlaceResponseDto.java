package com.server.booyoungee.domain.place.dto.response.movie;

import lombok.Builder;

@Builder
public record MoviePlaceResponseDto(
	Long id,
	String movieName,
	String movieCode,
	String name,
	String locationAddress,
	String description,
	String mapX,
	String mapY,
	String productionYear,
	String placeType
) {
	public MoviePlaceResponseDto {
		if (placeType == null || placeType.isBlank()) {
			placeType = "movie";
		}
	}
}
