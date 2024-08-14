package com.server.booyoungee.domain.BookMark.dto;

import com.server.booyoungee.domain.place.domain.PlaceType;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class BookMarksResponseDto {

	private Long id;
	private String name;
	private Double latitude;
	private Double longtidude;
	private PlaceType type;
	private String placeCategory;
	
}
