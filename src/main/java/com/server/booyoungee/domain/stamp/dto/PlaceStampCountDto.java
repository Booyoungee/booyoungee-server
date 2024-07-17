package com.server.booyoungee.domain.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceStampCountDto {
	private String placeId;
	private long count;
	private String type;
}