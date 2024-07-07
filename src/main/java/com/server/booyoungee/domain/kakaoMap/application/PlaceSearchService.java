package com.server.booyoungee.domain.kakaoMap.application;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.server.booyoungee.domain.kakaoMap.dto.KakaoKeywordResponseDto;
import com.server.booyoungee.domain.kakaoMap.dto.KeywordSearchDto;
import com.server.booyoungee.domain.kakaoMap.dto.response.SearchDetailDto;
import com.server.booyoungee.domain.kakaoMap.dto.response.SearchListResponseDto;
import com.server.booyoungee.domain.tourInfo.application.TourInfoOpenApiService;
import com.server.booyoungee.domain.tourInfo.dto.request.TourInfoRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceSearchService {
	private final KakaoAddressSearchService kakaoAddressSearchService;
	private final TourInfoOpenApiService tourInfoOpenApiService;

	@Value("${tourInfo.url}")
	private String baseUrl;

	@Value("${tourInfo.key}")
	private String serviceKey;

	@Value("${tourInfo.format}")
	private String _type;

	private final RestTemplate restTemplate;

	public SearchListResponseDto searchByKeywordWithRadius(String query, double x, double y, int radius, int page,
		int size) {
		KakaoKeywordResponseDto dto = kakaoAddressSearchService.searchByKeywordWithRadius(query, x, y, radius, page,
			size);
		SearchListResponseDto responseDto = SearchListResponseDto.builder()
			.keyword(dto.getMeta().getSameName().getKeyword())
			.region(dto.getMeta().getSameName().getSelectedRegion())
			.placesList(dto.getDocuments()).build();
		return responseDto;
	}

	public SearchListResponseDto searchByKeyword(String query, int page, int size) {
		KakaoKeywordResponseDto dto = kakaoAddressSearchService.searchByKeyword(query, page, size);
		SearchListResponseDto responseDto = SearchListResponseDto.builder()
			.keyword(dto.getMeta().getSameName().getKeyword())
			.region(dto.getMeta().getSameName().getSelectedRegion())
			.placesList(dto.getDocuments()).build();
		return responseDto;
	}

	public SearchDetailDto searchByKeywordDetails(String query) {
		KakaoKeywordResponseDto dto = kakaoAddressSearchService.searchByKeyword(query, 1, 5);

		KeywordSearchDto placeInfo = dto.getDocuments().stream()
			.filter(doc -> doc.getCategoryName().contains("관광"))
			.findFirst()
			.orElse(dto.getDocuments().get(0)); // Default to the first document if none match
		TourInfoRequestDto imageDto = getTourInfoByKeyword(1, 1, query);
		SearchDetailDto responseDto = SearchDetailDto.builder()
			.keyword(dto.getMeta().getSameName().getKeyword())
			.region(dto.getMeta().getSameName().getSelectedRegion())
			.info(placeInfo)
			.firstImage1(imageDto.getResponse().getBody().getItems().getItem().get(0).getFirstImage())
			.firstImage2(imageDto.getResponse().getBody().getItems().getItem().get(0).getFirstImage2())
			.build();
		return responseDto;
	}

	public TourInfoRequestDto getTourInfoByKeyword(int numOfRows, int pageNo, String keyword) {
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
			+ "&_type=" + "json";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<TourInfoRequestDto> response = restTemplate.exchange(
			requestUrl,
			HttpMethod.GET,
			entity,
			TourInfoRequestDto.class
		);

		return response.getBody();
	}

}
