package com.server.booyoungee.domain.kakaoMap.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.server.booyoungee.domain.kakaoMap.dto.KakaoApiResponseDto;
import com.server.booyoungee.domain.kakaoMap.dto.KakaoKeywordResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

	private final RestTemplate restTemplate;

	@Value("${KAKAO.API.KEY}")
	private String apiKey;

	private final String baseUrl = "https://dapi.kakao.com/v2/local";

	private <T> T callKakaoApi(String endpoint, UriComponentsBuilder uriBuilder, Class<T> responseType) {
		String url = uriBuilder.path(endpoint).toUriString();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + apiKey);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<T> response = restTemplate.exchange(
			url,
			HttpMethod.GET,
			entity,
			responseType
		);

		return response.getBody();
	}

	public KakaoApiResponseDto searchAddress(String query, int page, int size) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
			.queryParam("query", query)
			.queryParam("page", page)
			.queryParam("size", size)
			.queryParam("analyze_type", "similar");

		return callKakaoApi("/search/address.json", uriBuilder, KakaoApiResponseDto.class);
	}

	public KakaoApiResponseDto coordToRegionCode(double x, double y) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
			.queryParam("x", x)
			.queryParam("y", y);

		return callKakaoApi("/geo/coord2regioncode.json", uriBuilder, KakaoApiResponseDto.class);
	}

	public KakaoApiResponseDto coordToAddress(double x, double y) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
			.queryParam("x", x)
			.queryParam("y", y);

		return callKakaoApi("/geo/coord2address.json", uriBuilder, KakaoApiResponseDto.class);
	}

	public KakaoKeywordResponseDto searchByKeyword(String query, double x, double y, int radius, int page, int size) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
			.queryParam("query", query)
			.queryParam("x", x)
			.queryParam("y", y)
			.queryParam("radius", radius)
			.queryParam("page", page)
			.queryParam("size", size);

		return callKakaoApi("/search/keyword.json", uriBuilder, KakaoKeywordResponseDto.class);
	}

	public KakaoKeywordResponseDto searchByCategory(String categoryGroupCode, double x, double y, int radius, int page,
		int size) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
			.queryParam("category_group_code", categoryGroupCode)
			.queryParam("x", x)
			.queryParam("y", y)
			.queryParam("radius", radius)
			.queryParam("page", page)
			.queryParam("size", size);

		return callKakaoApi("/search/category.json", uriBuilder, KakaoKeywordResponseDto.class);
	}
}
