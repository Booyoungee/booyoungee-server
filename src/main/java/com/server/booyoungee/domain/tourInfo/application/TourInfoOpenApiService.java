package com.server.booyoungee.domain.tourInfo.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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

	public Object getTourInfoByLocation(int numOfRows, int pageNo, String mapX, String mapY, String radius) {
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

		return getTourInfo(requestUrl);
	}

	public Object getTourInfoByKeyword(int numOfRows, int pageNo, String keyword) {
		String requestUrl = baseUrl
			+ "/searchKeyword1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&keyword=" + keyword
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		return getTourInfo(requestUrl);
	}

	public Object getTourInfoByFestival(int numOfRows, int pageNo, String eventStartDate, String eventEndDate) {
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

		return getTourInfo(requestUrl);
	}

	public Object getTourInfoByStay(int numOfRows, int pageNo) {
		String requestUrl = baseUrl
			+ "/searchStay1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		return getTourInfo(requestUrl);
	}

	public Object getTourInfoByAreaCode(int numOfRows, int pageNo) {
		String requestUrl = baseUrl
			+ "/areaBasedList1"
			+ "?ServiceKey=" + serviceKey
			+ "&numOfRows=" + numOfRows
			+ "&pageNo=" + pageNo
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		return getTourInfo(requestUrl);
	}

	public Object getCommonInfoByContentId(String contentId) {
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

		return getTourInfo(requestUrl);
	}

	public Object getIntroInfoByContentId(String contentId, String contentTypeId) {
		String requestUrl = baseUrl
			+ "/detailIntro1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&contentTypeId=" + contentTypeId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&_type=" + _type;

		return getTourInfo(requestUrl);
	}

	public Object getImageInfoByContentId(String contentId) {
		String requestUrl = baseUrl
			+ "/detailImage1"
			+ "?ServiceKey=" + serviceKey
			+ "&contentId=" + contentId
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&imageYN=" + "Y"
			+ "&subImageYN=" + "Y"
			+ "&_type=" + _type;

		return getTourInfo(requestUrl);
	}

	public Object getAreaCode() {
		String url = baseUrl
			+ "/areaCode1"
			+ "?ServiceKey=" + serviceKey
			+ "&MobileOS=AND"
			+ "&MobileApp=booyoungee"
			+ "&areaCode=" + "6" // 부산 지역코드 : 6
			+ "&_type=" + _type;

		return getTourInfo(url);
	}

	private Object getTourInfo(String url) {
		HttpURLConnection urlConnection = null;
		Object jsonResult = null;
		InputStream stream;
		String result;

		try {
			URL requestUrl = new URL(url);

			urlConnection = (HttpURLConnection)requestUrl.openConnection();
			stream = getNetworkConnection(urlConnection);
			result = readStreamToString(stream);

			jsonResult = objectMapper.readValue(result, Object.class);

			if (stream != null)
				stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return jsonResult;
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
