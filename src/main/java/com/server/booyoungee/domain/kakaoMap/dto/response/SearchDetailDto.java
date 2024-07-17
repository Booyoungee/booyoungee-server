package com.server.booyoungee.domain.kakaoMap.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.server.booyoungee.domain.kakaoMap.dto.KeywordSearchDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchDetailDto {
	private String region;
	private String keyword;
	@JsonProperty("location_info")
	private KeywordSearchDto info;
	private String contentId;
	private String firstImage1;
	private String firstImage2;
	private String type;

}
