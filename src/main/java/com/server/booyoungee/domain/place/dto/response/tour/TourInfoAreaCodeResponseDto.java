package com.server.booyoungee.domain.place.dto.response.tour;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoAreaCodeResponseDto (
	@JsonProperty("rnum") Long rnum,
	@JsonProperty("code") String code,
	@JsonProperty("name") String name,
	@JsonProperty("placeType") String placeType

) {
	public TourInfoAreaCodeResponseDto {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
	}
}
