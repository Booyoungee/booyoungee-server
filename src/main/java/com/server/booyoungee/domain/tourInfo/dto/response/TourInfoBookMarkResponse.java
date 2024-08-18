package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoBookMarkResponse(

	@JsonProperty("contentid") String contentid,
	@JsonProperty("contenttypeid") String contenttypeid,
	@JsonProperty("mapx") String mapx,
	@JsonProperty("mapy") String mapy,
	@JsonProperty("title") String title,

	@JsonProperty("placeType") String placeType,

	String placeId,
	Long bookmarkId
) {
	public TourInfoBookMarkResponse {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
	}

	public static TourInfoBookMarkResponse fromPlace(String placeId, String title, String mapx, String mapy,
		String contentid, String placeType) {
		return TourInfoBookMarkResponse.builder()
			.placeId(placeId)
			.title(title)
			.placeType(placeType)
			.mapx(mapx)
			.mapy(mapy)
			.contentid(contentid)
			.build();
	}
}

