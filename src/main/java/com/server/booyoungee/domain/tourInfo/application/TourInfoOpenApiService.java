package com.server.booyoungee.domain.tourInfo.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoAreaCodeResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoAreaResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoCommonResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoDetailsResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoImageDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoImageResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoIntroResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoStayResponseDto;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkDetailsResponse;
import com.server.booyoungee.domain.tourInfo.dto.response.TourInfoBookMarkResponse;
import com.server.booyoungee.domain.tourInfo.exception.ListParsingErrorException;
import com.server.booyoungee.domain.tourInfo.exception.OpenApiCallErrorException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourInfoOpenApiService {
	private final ObjectMapper objectMapper;

	@Value("${tourInfo.url}")
	private String baseUrl;

	@Value("${tourInfo.key}")
	private String serviceKey;

	@Value("${tourInfo.format}")
	private String _type;

	public List<TourInfoCommonResponseDto> getTourInfoByLocation(int numOfRows, int pageNo, String mapX, String mapY, String radius) {
		String requestUrl = baseUrl
			+ "/locationBasedList1"
			+ "?ServiceKey=" + serviceKey
			+ "&_type=" + _type
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&mapX=" + mapX
			+ "&mapY=" + mapY
			+ "&radius=" + radius;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoCommonResponseDto[].class);
	}

	public List<TourInfoCommonResponseDto> getTourInfoByKeyword(int numOfRows, int pageNo, String keyword) {
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

		JsonNode jsonResult = getTourInfo(requestUrl);
		if (jsonResult == null || !jsonResult.isArray() || jsonResult.isEmpty()) {
			return Collections.emptyList();
		}
		return toList(jsonResult, TourInfoCommonResponseDto[].class);
	}

	public List<TourInfoCommonResponseDto> getTourInfoByFestival(int numOfRows, int pageNo, String eventStartDate, String eventEndDate) {
		String requestUrl = baseUrl
			+ "/searchFestival1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&eventStartDate=" + eventStartDate
			+ "&eventEndDate=" + eventEndDate
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoCommonResponseDto[].class);
	}

	public List<TourInfoStayResponseDto> getTourInfoByStay(int numOfRows, int pageNo) {
		String requestUrl = baseUrl
			+ "/searchStay1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoStayResponseDto[].class);
	}

	public List<TourInfoAreaResponseDto> getTourInfoByAreaCode(int numOfRows, int pageNo) {
		String requestUrl = baseUrl
			+ "/areaBasedList1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoAreaResponseDto[].class);
	}

	public List<TourInfoDetailsResponseDto> getCommonInfoByContentId(String contentId){
		String requestUrl = baseUrl
			+ "/detailCommon1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&defaultYN=" + "Y"
			+ "&firstImageYN=" + "Y"
			+ "&areacodeYN=" + "Y"
			+ "&catcodeYN=" + "Y"
			+ "&addrinfoYN=" + "Y"
			+ "&mapinfoYN=" + "Y"
			+ "&overviewYN=" + "Y"
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoDetailsResponseDto[].class);
	}

	public List<TourInfoIntroResponseDto> getIntroInfoByContentId(String contentId, String contentTypeId) {
		String requestUrl = baseUrl
			+ "/detailIntro1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&contentTypeId=" + contentTypeId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoIntroResponseDto[].class);
	}

	public List<TourInfoImageResponseDto> getImageInfoByContentId(String contentId) {
		String requestUrl = baseUrl
			+ "/detailImage1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&imageYN=" + "Y"
			+ "&subImageYN=" + "Y"
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoImageResponseDto[].class);
	}

	public List<TourInfoAreaCodeResponseDto> getAreaCode() {
		String requestUrl = baseUrl
			+ "/areaCode1"
			+ "?ServiceKey=" + serviceKey
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoAreaCodeResponseDto[].class);
	}

	public List<TourInfoBookMarkResponse> findByContentId(String contentId) {
		String requestUrl = baseUrl
			+ "/detailCommon1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&defaultYN=" + "Y"
			+ "&firstImageYN=" + "N"
			+ "&areacodeYN=" + "N"
			+ "&catcodeYN=" + "N"
			+ "&addrinfoYN=" + "N"
			+ "&mapinfoYN=" + "Y"
			+ "&overviewYN=" + "N"
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoBookMarkResponse[].class);
	}

	public List<TourInfoBookMarkDetailsResponse> findByTourInfoDetail(String contentId) {
		String requestUrl = baseUrl
			+ "/detailCommon1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&defaultYN=" + "Y"
			+ "&firstImageYN=" + "Y"
			+ "&areacodeYN=" + "N"
			+ "&catcodeYN=" + "N"
			+ "&addrinfoYN=" + "Y"
			+ "&mapinfoYN=" + "N"
			+ "&overviewYN=" + "N"
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		return toList(jsonResult, TourInfoBookMarkDetailsResponse[].class);
	}

	public List<TourInfoImageDto> getTourInfoImage(String keyword) {
		String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
		String requestUrl = baseUrl
			+ "/searchKeyword1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + 1
			+ "&pageNo=" + 0
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&keyword=" + encodedKeyword
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		JsonNode jsonResult = getTourInfo(requestUrl);
		if (jsonResult == null || !jsonResult.isArray() || jsonResult.isEmpty()) {
			return Collections.emptyList();
		}
		return toList(jsonResult, TourInfoImageDto[].class);
	}

	public JsonNode getTourInfo(String url) {
		HttpURLConnection urlConnection;
		InputStream stream;
		String result;
		JsonNode jsonResult;

		try {
			URL requestUrl = new URL(url);
			urlConnection = (HttpURLConnection)requestUrl.openConnection();
			stream = getNetworkConnection(urlConnection);
			result = readStreamToString(stream);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(result);
			jsonResult = rootNode.path("response").path("body").path("items").path("item");

			if (stream != null) {
				stream.close();
			}
			urlConnection.disconnect();
		} catch (Exception e) {
			throw new OpenApiCallErrorException();
		}
		return jsonResult;
	}

	public String getTitle(String stampId) {
		return getCommonInfoByContentId(stampId).get(0).title();
	}

	private <T> List<T> toList(JsonNode jsonResult, Class<T[]> clazz) {
		try {
			T[] array = objectMapper.treeToValue(jsonResult, clazz);
			return Arrays.asList(array);
		} catch (Exception e) {
			throw new ListParsingErrorException();
		}
	}


	private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
		urlConnection.setConnectTimeout(3000);
		urlConnection.setReadTimeout(3000);
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoInput(true);

		if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
		}

		return urlConnection.getInputStream();
	}

	private String readStreamToString(InputStream stream) throws IOException {
		StringBuilder result = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

		String readLine;
		while ((readLine = br.readLine()) != null) {
			result.append(readLine + "\n\r");
		}

		br.close();

		return result.toString();
	}
}
