package com.server.booyoungee.domain.place.dto.response;

import java.util.List;

import com.server.booyoungee.domain.place.domain.PlaceType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceDetailsDto {
	private String placeId;
	private String name;
	private String address;
	private List<String> images;
	private PlaceType type;

	private List<String> movies;
	private List<String> posterUrl;
}
