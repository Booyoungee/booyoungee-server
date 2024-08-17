package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoImageDto(

	@JsonProperty("contentid") String contentid,
	@JsonProperty("firstimage") String firstimage,
	@JsonProperty("firstimage2") String firstimage2
	
) {
}
