package com.server.booyoungee.domain.tourInfo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TourInfoResponseDto {
	private String contentId;
	private Long views;
}
