package com.server.booyoungee.domain.place.dto.response;

import java.util.List;

import com.server.booyoungee.domain.place.domain.PlaceType;

import lombok.Builder;

@Builder
public record PlaceDetailsResponse(
	String placeId,
	String name,
	String address,
	List<String> images,
	PlaceType type,
	List<String> movies,
	List<String> posterUrl
) {
	public static PlaceDetailsResponse of(String placeId, String name, String address, List<String> images, PlaceType type, List<String> movies, List<String> posterUrl) {
		return new PlaceDetailsResponse(placeId, name, address, images, type, movies, posterUrl);
	}
}
