package com.server.booyoungee.domain.login.application;

import org.springframework.stereotype.Service;

import com.server.booyoungee.domain.login.dto.SocialInfoDto;
import com.server.booyoungee.global.oauth.KakaoFeignClient;
import com.server.booyoungee.global.oauth.dto.KakaoUserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {
	private final KakaoFeignClient kakaoFeignClient;

	public SocialInfoDto getInfo(String providerToken) {
		KakaoUserDto kakaoUserdto = kakaoFeignClient.getUserInformation("Bearer " + providerToken);
		return SocialInfoDto.of(
			kakaoUserdto.id().toString(),
			kakaoUserdto.kakaoAccount().email(),
			kakaoUserdto.kakaoAccount().kakaoUserProfile().nickname());
	}
}