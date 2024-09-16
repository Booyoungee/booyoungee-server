package com.server.booyoungee.domain.place.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlaceDetailsListResponse(
    @Schema(
        description = "장소 상세 정보 목록",
        example = "[{\"placeId\":\"1\",\"name\":\"임랑해수욕장\",\"address\":\"부산광역시 기장군 장안읍 임랑해안길 51\",\"tel\":\"051-123-4567\",\"mapX\":\"129.244\",\"mapY\":\"35.244\",\"images\":[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"],\"type\":\"MOVIE\",\"movies\":[\"해운대\",\"극비수사\"],\"posterUrl\":[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"],\"likeCount\":1,\"starCount\":1,\"stampCount\":1,\"reviewCount\":1,\"bookmarkCount\":1}]",
        requiredMode = REQUIRED
    )
    List<PlaceDetailsResponse> contents
) {
    public static PlaceDetailsListResponse of(List<PlaceDetailsResponse> contents) {
        return new PlaceDetailsListResponse(contents);
    }
}
