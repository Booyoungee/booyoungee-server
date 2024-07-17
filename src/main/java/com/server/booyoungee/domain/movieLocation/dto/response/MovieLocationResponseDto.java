package com.server.booyoungee.domain.movieLocation.dto.response;

import lombok.Builder;

@Builder
public record MovieLocationResponseDto(
	Long id,
	String movieName,
	String movieCode,
	String locationName,
	String locationAddress,
	String description,
	String area,
	String mapx,
	String mapy,
	String productedAt
) {}
