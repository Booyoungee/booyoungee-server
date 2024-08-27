package com.server.booyoungee.domain.place.dto.response.tour;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record TourInfoCommonResponse(

	@Schema(description = "주소", example = "부산광역시 서구 암남동 620-53", requiredMode = REQUIRED)
	String addr1,

	@Schema(description = "상세주소", example = "암남공원 안", requiredMode = NOT_REQUIRED)
	String addr2,

	@Schema(description = "지역코드", example = "6", requiredMode = REQUIRED)
	String areacode,

	@Schema(description = "교과서 속 여행지 여부 (1:여행지, 0:해당없음)", example = "0", requiredMode = NOT_REQUIRED)
	String booktour,

	@Schema(description = "대분류", example = "A02", requiredMode = REQUIRED)
	String cat1,

	@Schema(description = "중분류", example = "A0205", requiredMode = REQUIRED)
	String cat2,

	@Schema(description = "소분류", example = "A02050100", requiredMode = REQUIRED)
	String cat3,

	@Schema(description = "관광지 ID", example = "2684738", requiredMode = REQUIRED)
	String contentid,

	@Schema(description = "관광지 타입 ID", example = "12", requiredMode = REQUIRED)
	String contenttypeid,

	@Schema(description = "최초 등록일", example = "20201123221338", requiredMode = REQUIRED)
	String createdtime,

	@Schema(description = "반경 거리", example = "5209.268824744433", requiredMode = REQUIRED)
	String dist,

	@Schema(description = "대표 이미지(원본)", example = "http://tong.visitkorea.or.kr/cms/resource/06/2684706_image2_1.jpg", requiredMode = NOT_REQUIRED)
	String firstimage,

	@Schema(description = "대표 이미지(썸네일)", example = "http://tong.visitkorea.or.kr/cms/resource/06/2684706_image2_1.jpg", requiredMode = NOT_REQUIRED)
	String firstimage2,

	@Schema(description = "저작권 유형 (Type1:출처 표시 권장, Type3:Type1 + 변경 금지)", example = "Type1", requiredMode = REQUIRED)
	String cpyrhtDivCd,

	@Schema(description = "GPS X좌표", example = "128.9832650955", requiredMode = REQUIRED)
	String mapx,

	@Schema(description = "GPS Y좌표", example = "35.0979240000", requiredMode = REQUIRED)
	String mapy,

	@Schema(description = "지도 확대 수준", example = "6", requiredMode = REQUIRED)
	String mlevel,

	@Schema(description = "수정일", example = "20201123221338", requiredMode = REQUIRED)
	String modifiedtime,

	@Schema(description = "시군구코드", example = "10", requiredMode = REQUIRED)
	String sigungucode,

	@Schema(description = "전화번호", example = "051-123-4567", requiredMode = NOT_REQUIRED)
	String tel,

	@Schema(description = "제목", example = "암남공원", requiredMode = REQUIRED)
	String title,

	@Schema(description = "우편번호", example = "49241", requiredMode = NOT_REQUIRED)
	String zipcode,

	@Schema(description = "관광지 타입 (TOUR:관광정보 Open API, MOVIE:영화 촬영지, STORE:지역 상생 식당)", example = "TOUR", requiredMode = NOT_REQUIRED)
	String placeType
) {
	public TourInfoCommonResponse {
		if (placeType == null || placeType.isBlank()) {
			placeType = "TOUR";
		}
	}
}
