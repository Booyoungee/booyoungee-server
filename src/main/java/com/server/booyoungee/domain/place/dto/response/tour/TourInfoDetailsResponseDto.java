package com.server.booyoungee.domain.place.dto.response.tour;

import com.server.booyoungee.domain.place.domain.tour.TourContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record TourInfoDetailsResponseDto(
    @Schema(description = "장소 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "관광지 고유 ID", example = "2786391", requiredMode = REQUIRED)
	String contentid,
	@Schema(description = "관광지 타입", example = "문화시설", requiredMode = REQUIRED)
	String contenttypeid,
    @Schema(description = "장소명", example = "희망통닭", requiredMode = REQUIRED)
	String title,

	//@JsonProperty("createdtime") String createdtime,
	//@JsonProperty("modifiedtime") String modifiedtime,

	@Schema(description = "전화번호", example = "051-555-0000", requiredMode = REQUIRED)
	String tel,

	@Schema(description = "장소사진1", example = "http://tong.visitkorea.or.kr/cms/resource/82/2787982_image2_1.jpg", requiredMode = REQUIRED)
	String firstimage,

	@Schema(description = "장소사진2", example = "http://tong.visitkorea.or.kr/cms/resource/82/2787982_image2_1.jpg", requiredMode = REQUIRED)
	String firstimage2,

	@Schema(description = "기본 주소", example = "부산광역시 동래구 명륜로", requiredMode = REQUIRED)
	String addr1,

	@Schema(description = "상세 주소", example = "98번길 94 희망통닭", requiredMode = REQUIRED)
	String addr2,

	@Schema(description = "우편번호", example = "12798", requiredMode = REQUIRED)
	String zipcode,

	@Schema(description = "경도", example = "129.0650146", requiredMode = REQUIRED)
	String mapx,

	@Schema(description = "위도", example = "35.0686809", requiredMode = REQUIRED)
	String mapy,

	//@JsonProperty("mlevel") String mlevel,
	//@JsonProperty("overview") String overview,
	@Schema(description = "장소 타입", example = "TOUR", requiredMode = REQUIRED)
	String placeType
) {
	public TourInfoDetailsResponseDto {
		if (placeType == null || placeType.isBlank()) {
			placeType = "tour";
		}
		TourContentType type = TourContentType.fromCode(contenttypeid);
		if (type != null) {
			contenttypeid = type.getDescription();
		}

	}

	public static TourInfoDetailsResponseDto from(final TourInfoDetailsResponseDto tourInfoDetailsResponseDto) {
		return new TourInfoDetailsResponseDto(
			tourInfoDetailsResponseDto.id(),
			tourInfoDetailsResponseDto.contentid(),
			tourInfoDetailsResponseDto.contenttypeid(),
			tourInfoDetailsResponseDto.title(),
			tourInfoDetailsResponseDto.tel(),
			tourInfoDetailsResponseDto.firstimage(),
			tourInfoDetailsResponseDto.firstimage2(),
			tourInfoDetailsResponseDto.addr1(),
			tourInfoDetailsResponseDto.addr2(),
			tourInfoDetailsResponseDto.zipcode(),
			tourInfoDetailsResponseDto.mapx(),
			tourInfoDetailsResponseDto.mapy(),
			tourInfoDetailsResponseDto.placeType()
		);
	}


}
