package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TourInfoDetailsResponseDto {
	@JsonProperty("contentid")
	private String contentid;

	@JsonProperty("contenttypeid")
	private String contenttypeid;

	@JsonProperty("title")
	private String title;

	@JsonProperty("createdtime")
	private String createdtime;

	@JsonProperty("modifiedtime")
	private String modifiedtime;

	@JsonProperty("tel")
	private String tel;

	@JsonProperty("telname")
	private String telname;

	@JsonProperty("homepage")
	private String homepage;

	@JsonProperty("booktour")
	private String booktour;

	@JsonProperty("firstimage")
	private String firstimage;

	@JsonProperty("firstimage2")
	private String firstimage2;

	@JsonProperty("cpyrhtDivCd")
	private String cpyrhtDivCd;

	@JsonProperty("areacode")
	private String areacode;

	@JsonProperty("sigungucode")
	private String sigungucode;

	@JsonProperty("cat1")
	private String cat1;

	@JsonProperty("cat2")
	private String cat2;

	@JsonProperty("cat3")
	private String cat3;

	@JsonProperty("addr1")
	private String addr1;

	@JsonProperty("addr2")
	private String addr2;

	@JsonProperty("zipcode")
	private String zipcode;

	@JsonProperty("mapx")
	private String mapx;

	@JsonProperty("mapy")
	private String mapy;

	@JsonProperty("mlevel")
	private String mlevel;

	@JsonProperty("overview")
	private String overview;
}
