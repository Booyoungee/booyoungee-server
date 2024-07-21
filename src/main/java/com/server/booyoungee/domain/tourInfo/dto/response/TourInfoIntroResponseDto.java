package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoIntroResponseDto(
	@JsonProperty("contentid") String contentid,
	@JsonProperty("contenttypeid") String contenttypeid,
	@JsonProperty("sponsor1") String sponsor1,
	@JsonProperty("sponsor1tel") String sponsor1tel,
	@JsonProperty("sponsor2") String sponsor2,
	@JsonProperty("sponsor2tel") String sponsor2tel,
	@JsonProperty("eventenddate") String eventenddate,
	@JsonProperty("playtime") String playtime,
	@JsonProperty("eventplace") String eventplace,
	@JsonProperty("eventhomepage") String eventhomepage,
	@JsonProperty("agelimit") String agelimit,
	@JsonProperty("bookingplace") String bookingplace,
	@JsonProperty("placeinfo") String placeinfo,
	@JsonProperty("subevent") String subevent,
	@JsonProperty("program") String program,
	@JsonProperty("eventstartdate") String eventstartdate,
	@JsonProperty("usetimefestival") String usetimefestival,
	@JsonProperty("discountinfofestival") String discountinfofestival,
	@JsonProperty("spendtimefestival") String spendtimefestival,
	@JsonProperty("festivalgrade") String festivalgrade,
	@JsonProperty("placeType") String placeType
) {
	public TourInfoIntroResponseDto {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
	}
}
