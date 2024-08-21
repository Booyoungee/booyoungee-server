package com.server.booyoungee.domain.place.dto.response.tour;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoImageResponseDto(
	@JsonProperty("contentid") String contentid,
	@JsonProperty("originimgurl") String originimgurl,
	@JsonProperty("imgname") String imgname,
	@JsonProperty("smallimageurl") String smallimageurl,
	@JsonProperty("cpyrhtDivCd") String cpyrhtDivCd,
	@JsonProperty("serialnum") String serialnum,
	@JsonProperty("placeType") String placeType

) {
	public TourInfoImageResponseDto {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
	}
}

