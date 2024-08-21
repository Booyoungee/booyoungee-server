package com.server.booyoungee.domain.place.dto.response.tour;

import com.server.booyoungee.domain.place.domain.tour.TourPlace;
import lombok.Builder;

@Builder
public record TourPlaceResponseDto(
        Long id,
        String contentId,
        String contentTypeId


) {
    public static TourPlaceResponseDto from(final TourPlace place){
        return TourPlaceResponseDto.builder()
                .id(place.getId())
                .contentId(place.getContentId())
                .contentTypeId(place.getContentTypeId())
                .build();
    }
}


