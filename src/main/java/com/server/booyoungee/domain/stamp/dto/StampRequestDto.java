package com.server.booyoungee.domain.stamp.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StampRequestDto {

	private String placeId;

	private double userX; //현재 위치 x
	private double userY; //현재 위치 y

	private double x;

	private double y;
}
