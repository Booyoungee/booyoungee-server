package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoDetailsResponseDto(
	@JsonProperty("contentid") String contentid,
	@JsonProperty("contenttypeid") String contenttypeid,
	@JsonProperty("title") String title,
	@JsonProperty("createdtime") String createdtime,
	@JsonProperty("modifiedtime") String modifiedtime,
	@JsonProperty("tel") String tel,
	@JsonProperty("telname") String telname,
	@JsonProperty("homepage") String homepage,
	@JsonProperty("booktour") String booktour,
	@JsonProperty("firstimage") String firstimage,
	@JsonProperty("firstimage2") String firstimage2,
	@JsonProperty("cpyrhtDivCd") String cpyrhtDivCd,
	@JsonProperty("areacode") String areacode,
	@JsonProperty("sigungucode") String sigungucode,
	@JsonProperty("cat1") String cat1,
	@JsonProperty("cat2") String cat2,
	@JsonProperty("cat3") String cat3,
	@JsonProperty("addr1") String addr1,
	@JsonProperty("addr2") String addr2,
	@JsonProperty("zipcode") String zipcode,
	@JsonProperty("mapx") String mapx,
	@JsonProperty("mapy") String mapy,
	@JsonProperty("mlevel") String mlevel,
	@JsonProperty("overview") String overview,
	@JsonProperty("placeType") String placeType
) {
	public TourInfoDetailsResponseDto {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
	}
}
