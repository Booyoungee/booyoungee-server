package com.server.booyoungee.domain.place.dto.response.tour;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoResponseDto(
	@JsonProperty("contentId") String contentId,
	@JsonProperty("views") Long views,
	@JsonProperty("description") String description,
	@JsonProperty("placeType") String placeType,

	String title

) {
	public TourInfoResponseDto {
		if (description == null || description.isBlank()) {
			description = "tour";
		}
	}
}
