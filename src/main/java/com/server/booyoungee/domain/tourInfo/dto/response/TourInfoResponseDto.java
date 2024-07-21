package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoResponseDto(
	@JsonProperty("contentId") String contentId,
	@JsonProperty("views") Long views,
	@JsonProperty("description") String description,
	@JsonProperty("placeType") String placeType
) {
	public TourInfoResponseDto {
		if (description == null || description.isBlank()) {
			description = "tour";
		}
	}
}
