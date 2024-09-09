package com.server.booyoungee.domain.place.dto.response;

import java.util.List;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record PlaceDetailsResponse(

	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	String placeId,

	@Schema(description = "장소명", example = "임랑해수욕장", requiredMode = REQUIRED)
	String name,

	@Schema(description = "기본 주소", example = "부산광역시 기장군 장안읍 임랑해안길 51", requiredMode = REQUIRED)
	String address,

	@Schema(description = "전화번호", example = "051-123-4567", requiredMode = NOT_REQUIRED)
	String tel,

	@Schema(description = "이미지 URL", example = "[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"]", requiredMode = REQUIRED)
	List<String> images,

	@Schema(description = "장소 타입", example = "MOVIE", requiredMode = REQUIRED)
	PlaceType type,

	@Schema(description = "영화 목록", example = "[\"해운대\",\"극비수사\"]", requiredMode = NOT_REQUIRED)
	List<String> movies,

	@Schema(description = "포스터 URL", example = "[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"]", requiredMode = NOT_REQUIRED)
	List<String> posterUrl,

	@Schema(description = "좋아요 수", example = "1", requiredMode = REQUIRED)
	int likeCount,

	@Schema(description = "별점 수", example = "1", requiredMode = REQUIRED)
	int starCount,

	@Schema(description = "스탬프 수", example = "1", requiredMode = REQUIRED)
	int stampCount,

	@Schema(description = "장소에 대한 사용자 정보",
			example = "{\"hasStamp\":true,\"hasLike\":true,\"hasBookmark\":true}",
			requiredMode = REQUIRED)
	UserMeResponse me
) {
	public static PlaceDetailsResponse of(String placeId, String name, String address, String tel, List<String> images,
		PlaceType type, List<String> movies, List<String> posterUrl, int likeCount, int starCount,int stampCount,UserMeResponse me) {
		return new PlaceDetailsResponse(placeId, name, address, tel, images, type, movies, posterUrl, likeCount,
			starCount,stampCount,me);
	}

	public static PlaceDetailsResponse from(Place place,PlaceType type,String tel,List<String> images, List<String> movies, List<String> posterUrl,UserMeResponse me) {
		return new PlaceDetailsResponse(place.getId()+"", place.getName(), place.getBasicAddress(), tel, images, type, movies, posterUrl, place.getLikes().size(), 0,place.getStamps().size(),me);
	}
}
