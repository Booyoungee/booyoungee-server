package com.server.booyoungee.domain.place.dto.response.recommend;

import java.util.List;

import com.server.booyoungee.domain.place.dto.response.PlaceDetailsResponse;
import io.swagger.v3.oas.annotations.media.Schema;

public record RecommendPlaceListResponse(

	@Schema
		(
			description = "추천 장소 목록",
			example = "[{\"placeId\":\"1\",\"name\":\"임랑해수욕장\",\"address\":\"경상북도 영덕군 임랑면 임랑해변길 1\",\"images\":[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"],\"type\":\"MOVIE\",\"movies\":[\"해운대\",\"극비수사\"],\"posterUrl\":[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"],\"likeCount\":1,\"starCount\":1}]",
			required = true
		)
	List<PlaceDetailsResponse> contents
) {

	public static RecommendPlaceListResponse of(List<PlaceDetailsResponse> content) {
		return new RecommendPlaceListResponse(content);
	}
}
