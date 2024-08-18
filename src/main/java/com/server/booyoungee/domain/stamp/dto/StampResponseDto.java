package com.server.booyoungee.domain.stamp.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public record StampResponseDto(

	Long stampId,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	LocalDateTime createdAt,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	LocalDateTime updatedAt,
	String placeId,
	String placeName,
	String type,
	long count
) {

}
