package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TourInfoIntroResponseDto {
	@JsonProperty("contentid")
	private String contentid;

	@JsonProperty("contenttypeid")
	private String contenttypeid;

	@JsonProperty("sponsor1")
	private String sponsor1;

	@JsonProperty("sponsor1tel")
	private String sponsor1tel;

	@JsonProperty("sponsor2")
	private String sponsor2;

	@JsonProperty("sponsor2tel")
	private String sponsor2tel;

	@JsonProperty("eventenddate")
	private String eventenddate;

	@JsonProperty("playtime")
	private String playtime;

	@JsonProperty("eventplace")
	private String eventplace;

	@JsonProperty("eventhomepage")
	private String eventhomepage;

	@JsonProperty("agelimit")
	private String agelimit;

	@JsonProperty("bookingplace")
	private String bookingplace;

	@JsonProperty("placeinfo")
	private String placeinfo;

	@JsonProperty("subevent")
	private String subevent;

	@JsonProperty("program")
	private String program;

	@JsonProperty("eventstartdate")
	private String eventstartdate;

	@JsonProperty("usetimefestival")
	private String usetimefestival;

	@JsonProperty("discountinfofestival")
	private String discountinfofestival;

	@JsonProperty("spendtimefestival")
	private String spendtimefestival;

	@JsonProperty("festivalgrade")
	private String festivalgrade;
}
