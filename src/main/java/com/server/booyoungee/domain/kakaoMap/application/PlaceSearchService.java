package com.server.booyoungee.domain.kakaoMap.application;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.UnknownContentTypeException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.booyoungee.domain.kakaoMap.dto.KakaoKeywordResponseDto;
import com.server.booyoungee.domain.kakaoMap.dto.KeywordSearchDto;
import com.server.booyoungee.domain.kakaoMap.dto.response.SearchDetailDto;
import com.server.booyoungee.domain.kakaoMap.dto.response.SearchListResponseDto;
import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.tourInfo.application.TourInfoService;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoCommonResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceSearchService {
	private final KakaoAddressSearchService kakaoAddressSearchService;
	private final TourInfoOpenApiService tourInfoOpenApiService;
	private final TourInfoService tourInfoService;
	private final ObjectMapper objectMapper;

	@Value("${tourInfo.url}")
	private String baseUrl;

	@Value("${tourInfo.key}")
	private String serviceKey;

	@Value("${tourInfo.format}")
	private String _type;

	public SearchListResponseDto searchByKeywordWithRadius(String query, double x, double y, int radius, int page,
		int size) {
		KakaoKeywordResponseDto dto = kakaoAddressSearchService.searchByKeywordWithRadius(query, x, y, radius, page,
			size);
		SearchListResponseDto responseDto = SearchListResponseDto.builder()
			.keyword(dto.getMeta().getSameName().getKeyword())
			.region(dto.getMeta().getSameName().getSelectedRegion())
			.type("tour")
			.placesList(dto.getDocuments()).build();
		return responseDto;
	}

	public SearchListResponseDto searchByKeyword(String query, int page, int size) {
		KakaoKeywordResponseDto dto = kakaoAddressSearchService.searchByKeyword(query, page, size);
		SearchListResponseDto responseDto = SearchListResponseDto.builder()
			.keyword(dto.getMeta().getSameName().getKeyword())
			.region(dto.getMeta().getSameName().getSelectedRegion())
			.type("tour")
			.placesList(dto.getDocuments()).build();
		return responseDto;
	}

	public SearchDetailDto searchByKeywordDetails(String query) throws IOException {
		KakaoKeywordResponseDto dto = kakaoAddressSearchService.searchByKeyword(query, 1, 5);

		KeywordSearchDto placeInfo = dto.getDocuments().stream()
			.filter(doc -> doc.getCategoryName().contains("관광"))
			.findFirst()
			.orElse(dto.getDocuments().get(0)); // Default to the first document if none match
		List<TourInfoCommonResponseDto> tourInfo = getTourInfoByKeyword(10, 1, query);

		String firstImage1 = null;
		String firstImage2 = null;
		String contentId = null;
		String type = null;

		if (tourInfo != null && !tourInfo.isEmpty()) {
			contentId = tourInfo.get(0).contentid();
			type = tourInfo.get(0).contenttypeid();
			tourInfoService.viewContent(contentId, type);
			firstImage1 = tourInfo.get(0).firstimage();
			firstImage2 = tourInfo.get(0).firstimage2();
		}
		SearchDetailDto responseDto = SearchDetailDto.builder()
			.keyword(dto.getMeta().getSameName().getKeyword())
			.region(dto.getMeta().getSameName().getSelectedRegion())
			.info(placeInfo)
			.firstImage1(firstImage1)
			.firstImage2(firstImage2)
			.contentId(contentId)
			.type("tour")
			.build();
		return responseDto;
	}

	public List<TourInfoCommonResponseDto> getTourInfoByKeyword(int numOfRows, int pageNo, String keyword) {
		try {
			String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
			String requestUrl = baseUrl
				+ "/searchKeyword1"
				+ "?ServiceKey=" + serviceKey
				+ "&numOfRows=" + numOfRows
				+ "&pageNo=" + pageNo
				+ "&MobileOS=AND"
				+ "&MobileApp=booyoungee"
				+ "&keyword=" + encodedKeyword
				+ "&areaCode=" + "6" // 부산 지역코드 : 6
				+ "&_type=" + _type;

			JsonNode jsonResult = tourInfoOpenApiService.getTourInfo(requestUrl);
			return Arrays.asList(objectMapper.treeToValue(jsonResult, TourInfoCommonResponseDto[].class));
		} catch (UnknownContentTypeException e) {
			System.err.println("Content type error: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.err.println("IO error: " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.err.println("An error occurred: " + e.getMessage());
			return null;
		}
	}

	public SearchDetailDto searchByKeywordDetailsAddtypeOption(String query, String apiType) throws IOException {
		KakaoKeywordResponseDto dto = kakaoAddressSearchService.searchByKeyword(query, 1, 5);

		KeywordSearchDto placeInfo = dto.getDocuments().stream()
			.filter(doc -> doc.getCategoryName().contains("관광"))
			.findFirst()
			.orElse(dto.getDocuments().get(0)); // Default to the first document if none match
		List<TourInfoCommonResponseDto> tourInfo = getTourInfoByKeyword(10, 1, query);

		String firstImage1 = null;
		String firstImage2 = null;
		String contentId = null;
		String type = null;

		if (tourInfo != null && !tourInfo.isEmpty()) {
			contentId = tourInfo.get(0).getContentid();
			type = tourInfo.get(0).getContenttypeid();
			tourInfoService.viewContent(contentId, type);
			firstImage1 = tourInfo.get(0).getFirstimage();
			firstImage2 = tourInfo.get(0).getFirstimage2();
		}
		SearchDetailDto responseDto = SearchDetailDto.builder()
			.keyword(dto.getMeta().getSameName().getKeyword())
			.region(dto.getMeta().getSameName().getSelectedRegion())
			.info(placeInfo)
			.firstImage1(firstImage1)
			.firstImage2(firstImage2)
			.contentId(contentId)
			.type(apiType)
			.build();
		return responseDto;
	}

}
