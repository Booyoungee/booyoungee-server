package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoBookMarkDetailsResponse(
	@JsonProperty("contentid") String contentid,
	@JsonProperty("firstimage") String firstimage,
	@JsonProperty("firstimage2") String firstimage2,

	@JsonProperty("addr1") String addr1,
	@JsonProperty("addr2") String addr2,
	@JsonProperty("title") String title,

	@JsonProperty("placeType") String placeType,

	String placeId

) {
	public TourInfoBookMarkDetailsResponse {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
	}

	public static TourInfoBookMarkDetailsResponse fromPlace(String placeId, String title, String addr1, String addr2,
		String contentid, String placeType) {
		return TourInfoBookMarkDetailsResponse.builder()
			.placeId(placeId)
			.title(title)
			.placeType(placeType)
			.addr1(addr1)
			.addr2(addr2)
			.contentid(contentid)
			.build();
	}
}

