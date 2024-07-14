package com.server.booyoungee.domain.login.application;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.server.booyoungee.domain.login.dto.SocialInfoDto;
import com.server.booyoungee.global.oauth.KakaoFeignClient;
import com.server.booyoungee.global.oauth.dto.KakaoUserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {
	private final KakaoFeignClient kakaoFeignClient;

	private final RestTemplate restTemplate;

	public SocialInfoDto getInfo(String providerToken) {
		KakaoUserDto kakaoUserdto = kakaoFeignClient.getUserInformation("Bearer " + providerToken);
		return SocialInfoDto.of(
			kakaoUserdto.id().toString(),
			kakaoUserdto.kakaoAccount().email(),
			//kakaoUserdto.kakaoAccount().email(),
			kakaoUserdto.kakaoAccount().kakaoUserProfile().nickname());
	}

	public String getAccessToken(String code, String clientId, String redirectUri) {
		String tokenUri = "https://kauth.kakao.com/oauth/token";

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(tokenUri)
			.queryParam("grant_type", "authorization_code")
			.queryParam("client_id", clientId)
			.queryParam("redirect_uri", redirectUri)
			.queryParam("code", code);

		System.out.println(uriBuilder.toUriString());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<Map> response = restTemplate.exchange(
				uriBuilder.toUriString(),
				HttpMethod.POST,
				entity,
				Map.class
			);

			Map<String, Object> responseBody = response.getBody();
			if (responseBody != null && responseBody.containsKey("access_token")) {
				return (String)responseBody.get("access_token");
			} else {
				throw new RuntimeException("Failed to get access token from Kakao: " + responseBody);
			}
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("HTTP error while getting access token from Kakao: " + e.getStatusCode() + " - "
				+ e.getResponseBodyAsString(), e);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error while getting access token from Kakao", e);
		}
	}

}