package com.server.booyoungee.domain.tourInfo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TourInfoAreaCodeResponseDto {
	@JsonProperty("rnum")
	private Long rnum;

	@JsonProperty("code")
	private String code;

	@JsonProperty("name")
	private String name;
}
