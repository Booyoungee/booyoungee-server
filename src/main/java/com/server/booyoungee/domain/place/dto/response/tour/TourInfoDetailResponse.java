package com.server.booyoungee.domain.place.dto.response.tour;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoDetailResponse(

	@JsonProperty("contentid") String contentid,
	@JsonProperty("firstimage") String firstimage,
	@JsonProperty("firstimage2") String firstimage2,

	@JsonProperty("tel") String tel

) {
}
