package com.server.booyoungee.domain.place.dto.response.hotPlace;

import java.util.List;

import com.server.booyoungee.domain.place.domain.HotPlace;

public record HotPlacePersistReponse(
	List<Long> id
) {
	public static HotPlacePersistReponse from(List<HotPlace> id) {
		return new HotPlacePersistReponse(
			id.stream().map(HotPlace::getId).toList()
		);
	}
}
