package com.server.booyoungee.domain.place.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import com.server.booyoungee.domain.place.domain.Place;
import com.server.booyoungee.domain.place.domain.PlaceType;
import com.server.booyoungee.domain.place.domain.movie.MoviePlace;
import com.server.booyoungee.domain.place.domain.tour.TourPlace;
import com.server.booyoungee.domain.review.stars.domain.Stars;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PlaceSummaryResponse(
	@Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	String id,

	@Schema(description = "장소명", example = "임랑해수욕장", requiredMode = REQUIRED)
	String name,

	@Schema(description = "기본 주소", example = "부산광역시 기장군 장안읍 임랑해안길 51", requiredMode = REQUIRED)
	String basicAddress,

	@Schema(description = "별점", example = "4.7", requiredMode = REQUIRED)
	double stars,

	@Schema(description = "좋아요 수", example = "12", requiredMode = REQUIRED)
	int likes,

	@Schema(description = "리뷰 수", example = "11", requiredMode = REQUIRED)
	int reviews,

	@Schema(description = "영화 제목", example = "극비 수사", requiredMode = NOT_REQUIRED)
	String movieName,

	@Schema(description = "장소 타입", example = "MOVIE", requiredMode = REQUIRED)
	PlaceType type,

	@Schema(description = "이미지 URL", example = "[\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장1.jpg\",\"https://booyoungee.s3.ap-northeast-2.amazonaws.com/임랑해수욕장2.jpg\"]", requiredMode = REQUIRED)
	List<String> images
) {
	public static PlaceSummaryResponse of(TourPlace tourPlace, String tourPlaceName, String tourPlaceBasicAddress,
		List<Stars> stars, int likes, int reviews, PlaceType type, List<String> images) {
		return PlaceSummaryResponse.builder()
			.id(tourPlace.getId() + "")
			.name(tourPlaceName)
			.basicAddress(tourPlaceBasicAddress)
			.stars(stars.stream().mapToDouble(Stars::getStars).average().orElse(0))
			.likes(likes)
			.reviews(reviews)
			.type(type)
			.images(images)
			.build();
	}

	public static PlaceSummaryResponse of(Place place, List<Stars> stars, int likes, int reviews, PlaceType type,
		List<String> images) {
		return PlaceSummaryResponse.builder()
			.id(place.getId() + "")
			.name(place.getName())
			.basicAddress(place.getBasicAddress())
			.stars(stars.stream().mapToDouble(Stars::getStars).average().orElse(0))
			.likes(likes)
			.reviews(reviews)
			.type(type)
			.images(images)
			.build();
	}

	public static PlaceSummaryResponse of(MoviePlace moviePlace, List<Stars> stars, int likes, int reviews,
		PlaceType type, List<String> images) {
		return PlaceSummaryResponse.builder()
			.id(moviePlace.getId() + "")
			.name(moviePlace.getName())
			.basicAddress(moviePlace.getBasicAddress())
			.stars(stars.stream().mapToDouble(Stars::getStars).average().orElse(0))
			.likes(likes)
			.reviews(reviews)
			.movieName(moviePlace.getMovieName())
			.type(type)
			.images(images)
			.build();
	}
}
