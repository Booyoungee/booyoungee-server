package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TourInfoAreaResponseDto(
	@JsonProperty("addr1") String addr1,
	@JsonProperty("addr2") String addr2,
	@JsonProperty("areacode") String areacode,
	@JsonProperty("booktour") String booktour,
	@JsonProperty("cat1") String cat1,
	@JsonProperty("cat2") String cat2,
	@JsonProperty("cat3") String cat3,
	@JsonProperty("contentid") String contentid,
	@JsonProperty("contenttypeid") String contenttypeid,
	@JsonProperty("createdtime") String createdtime,
	@JsonProperty("firstimage") String firstimage,
	@JsonProperty("firstimage2") String firstimage2,
	@JsonProperty("cpyrhtDivCd") String cpyrhtDivCd,
	@JsonProperty("mapx") String mapx,
	@JsonProperty("mapy") String mapy,
	@JsonProperty("mlevel") String mlevel,
	@JsonProperty("modifiedtime") String modifiedtime,
	@JsonProperty("sigungucode") String sigungucode,
	@JsonProperty("tel") String tel,
	@JsonProperty("title") String title,
	@JsonProperty("zipcode") String zipcode,
	@JsonProperty("placeType") String placeType
) {
	public TourInfoAreaResponseDto {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
	}
}
