package com.server.booyoungee.domain.place.dto.response.hotPlace;

import java.util.List;

import com.server.booyoungee.domain.place.domain.HotPlace;

public record HotPlacePersistResponse(
	List<Long> id
) {
	public static HotPlacePersistResponse from(List<HotPlace> id) {
		return new HotPlacePersistResponse(
			id.stream().map(HotPlace::getId).toList()
		);
	}
}
