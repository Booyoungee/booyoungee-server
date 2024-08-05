package com.server.booyoungee.domain.place.dto.response.hotPlace;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class HotPlaceResponseDto {

    @Column(nullable = false)
    private Long placeId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
