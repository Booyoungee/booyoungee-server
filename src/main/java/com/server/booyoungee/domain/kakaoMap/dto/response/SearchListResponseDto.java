package com.server.booyoungee.domain.kakaoMap.dto.response;

import java.util.List;

import com.server.booyoungee.domain.kakaoMap.dto.KeywordSearchDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchListResponseDto {

	private String region;
	private String keyword;
	private List<KeywordSearchDto> placesList;
	private String type;

}
