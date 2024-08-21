package com.server.booyoungee.domain.place.dto.response.tour;

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

	Long placeId,
	Long bookmarkId
) {
	public TourInfoBookMarkResponse {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
	}

	public static TourInfoBookMarkResponse of(Long id, Long placeId, String title, String mapx, String mapy,
		String contentid, String placeType, String contenttypeid) {
		return TourInfoBookMarkResponse.builder()
			.bookmarkId(id)
			.placeId(placeId)
			.title(title)
			.placeType(placeType)
			.mapx(mapx)
			.mapy(mapy)
			.contentid(contentid)
			.contenttypeid(contenttypeid)
			.build();
	}
}

