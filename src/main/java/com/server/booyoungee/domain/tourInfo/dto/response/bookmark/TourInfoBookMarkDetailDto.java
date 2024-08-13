package com.server.booyoungee.domain.tourInfo.dto.response.bookmark;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoBookMarkDetailDto(
	@JsonProperty("contentid") String contentid,
	@JsonProperty("firstimage") String firstimage,
	@JsonProperty("firstimage2") String firstimage2,

	@JsonProperty("addr1") String addr1,
	@JsonProperty("addr2") String addr2,
	@JsonProperty("title") String title,

	@JsonProperty("placeType") String placeType,

	String placeId

) {

}

