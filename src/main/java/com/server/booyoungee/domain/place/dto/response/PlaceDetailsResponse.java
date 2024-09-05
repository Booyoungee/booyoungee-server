package com.server.booyoungee.domain.place.dto.response;

import java.util.List;

import com.server.booyoungee.domain.place.domain.PlaceType;

import lombok.Builder;

@Builder
public record PlaceDetailsResponse(
	String placeId,
	String name,
	String address,

	String tel,
	List<String> images,
	PlaceType type,
	List<String> movies,
	List<String> posterUrl,

	int likeCount,

	int starCount
) {
	public static PlaceDetailsResponse of(String placeId, String name, String address, String tel, List<String> images,
		PlaceType type, List<String> movies, List<String> posterUrl, int likeCount, int starCount) {
		return new PlaceDetailsResponse(placeId, name, address, tel, images, type, movies, posterUrl, likeCount,
			starCount);
	}
}
