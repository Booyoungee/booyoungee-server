package com.server.booyoungee.domain.place.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.review.stars.domain.Stars;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

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

	// TODO : mapX mapY 실제 값 넣어주세요
	@Schema(description = "경도", example = "129.244", requiredMode = REQUIRED)
	String mapX,

	@Schema(description = "위도", example = "35.244", requiredMode = REQUIRED)
	String mapY,

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

	@Schema(description = "리뷰 수", example = "1", requiredMode = REQUIRED)
	int reviewCount,

	@Schema(description = "북마크 수", example = "1", requiredMode = REQUIRED)
	int bookmarkCount,

	@Schema(description = "별점", example = "4.7", requiredMode = REQUIRED)
	double stars,

	@Schema(description = "장소에 대한 사용자 정보",
		example = "{\"hasStamp\":true,\"hasLike\":true,\"hasBookmark\":true}",
		requiredMode = REQUIRED)
	UserMeResponse me
) {
	public static PlaceDetailsResponse of(String placeId, String name, String address, String tel, List<String> images,
		PlaceType type, List<String> movies, List<String> posterUrl, int likeCount, int starCount, int stampCount,
		int reviewCount, int bookmarkCount, List<Stars> stars, UserMeResponse me) {
		return PlaceDetailsResponse.builder()
			.placeId(placeId)
			.name(name)
			.address(address)
			.tel(tel)
			.mapX("129.244")
			.mapY("35.244")
			.images(images)
			.type(type)
			.movies(movies)
			.posterUrl(posterUrl)
			.likeCount(likeCount)
			.starCount(starCount)
			.stampCount(stampCount)
			.reviewCount(reviewCount)
			.bookmarkCount(bookmarkCount)
			.stars(stars.stream().mapToDouble(Stars::getStars).average().orElse(0))
			.me(me)
			.build();
	}

	public static PlaceDetailsResponse from(Place place, PlaceType type, String tel, List<String> images,
		List<String> movies, List<String> posterUrl, List<Stars> stars, UserMeResponse me) {
		return PlaceDetailsResponse.builder()
			.placeId(place.getId() + "")
			.name(place.getName())
			.address(place.getBasicAddress())
			.tel(tel)
			.mapX("129.244")
			.mapY("35.244")
			.images(images)
			.type(type)
			.movies(movies)
			.posterUrl(posterUrl)
			.likeCount(place.getLikes().size())
			.starCount(0)
			.stampCount(place.getStamps().size())
			.reviewCount(place.getComments().size())
			.bookmarkCount(place.getBookmarks().size())
			.stars(stars.stream().mapToDouble(Stars::getStars).average().orElse(0))
			.me(me)
			.build();
	}
}
