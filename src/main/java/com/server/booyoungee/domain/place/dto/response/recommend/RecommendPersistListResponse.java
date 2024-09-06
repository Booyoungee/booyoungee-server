package com.server.booyoungee.domain.place.dto.response.recommend;

import com.server.booyoungee.domain.place.domain.HotPlace;
import com.server.booyoungee.domain.place.domain.RecommendPlace;
import com.server.booyoungee.domain.place.dto.response.hotPlace.HotPlacePersistResponse;

import java.util.List;

public record RecommendPersistListResponse(
        List<Long> recommendIds
) {

    public static RecommendPersistListResponse from(List<RecommendPlace> id) {
        return new RecommendPersistListResponse(
                id.stream().map(RecommendPlace::getId).toList()
        );
    }
}
