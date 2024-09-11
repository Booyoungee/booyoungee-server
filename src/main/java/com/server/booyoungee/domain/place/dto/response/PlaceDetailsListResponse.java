package com.server.booyoungee.domain.place.dto.response;

import java.util.List;

public record PlaceDetailsListResponse(
    List<PlaceDetailsResponse> contents
) {
    public static PlaceDetailsListResponse of(List<PlaceDetailsResponse> contents) {
        return new PlaceDetailsListResponse(contents);
    }
}
