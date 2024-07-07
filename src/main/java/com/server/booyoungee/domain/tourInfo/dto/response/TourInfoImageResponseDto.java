package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TourInfoImageResponseDto {
	@JsonProperty("contentid")
	private String contentid;

	@JsonProperty("originimgurl")
	private String originimgurl;

	@JsonProperty("imgname")
	private String imgname;

	@JsonProperty("smallimageurl")
	private String smallimageurl;

	@JsonProperty("cpyrhtDivCd")
	private String cpyrhtDivCd;

	@JsonProperty("serialnum")
	private String serialnum;
}
