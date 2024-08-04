package com.server.booyoungee.domain.login.application;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.server.booyoungee.domain.login.domain.Constants;
import com.server.booyoungee.domain.login.domain.enums.Provider;
import com.server.booyoungee.domain.login.dto.SocialInfoDto;
import com.server.booyoungee.domain.login.dto.request.LoginRequestDto;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.domain.user.dao.UserRepository;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.exception.ErrorCode;
import com.server.booyoungee.global.oauth.dto.KakaoTokenResponse;
import com.server.booyoungee.global.oauth.security.info.UserAuthentication;
import com.server.booyoungee.global.utils.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final KakaoLoginService kakaoLoginService;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final RestTemplate restTemplate;

	@Transactional
	public JwtTokenResponse login(KakaoTokenResponse providerToken, LoginRequestDto request) throws IOException {
		SocialInfoDto socialInfo = getSocialInfo(request, providerToken.getAccess_token());
		User user = loadOrCreateUser(request.provider(), socialInfo, providerToken);
		System.out.println("login");
		String refreshToken = providerToken.getRefresh_token();
		if (refreshToken == null) {
			refreshToken = user.getRefreshToken();
		}
		return generateTokensWithUpdateRefreshToken(user, providerToken.getAccess_token(), refreshToken);
	}

	@Transactional
	public JwtTokenResponse signup(String id, String name) throws IOException {

		Optional<User> user = userRepository.findBySerialId(id);
		return generateTokensWithUpdateRefreshToken(user.get(), name);
	}

	private SocialInfoDto getSocialInfo(LoginRequestDto request, String providerToken) {
		if (request.provider().toString().equals(Provider.KAKAO.toString())) {
			return kakaoLoginService.getInfo(providerToken);
		} else {
			throw new CustomException(ErrorCode.NULL_POINT_ERROR);
		}
	}

	private User loadOrCreateUser(Provider provider, SocialInfoDto socialInfo, KakaoTokenResponse providerToken) {
		return userRepository.findBySerialIdAndName(socialInfo.serialId())
			.orElseGet(() -> {
				User newUser = User.builder()
					.serialId(socialInfo.serialId())
					.email(socialInfo.email())
					//name(socialInfo.name())
					.name("")
					.role(User.Role.USER)
					.refreshToken(providerToken.getRefresh_token() + "|"
						+ providerToken.getAccess_token()) // Initialize refreshToken as empty string
					.build();
				userRepository.save(newUser);
				System.out.println("new User");
				throw new CustomException(ErrorCode.NEW_USER, socialInfo.serialId());
			});
	}

	private JwtTokenResponse generateTokensWithUpdateRefreshToken(User user, String accessToken, String refreshToken) {
		JwtTokenResponse jwtTokenResponse = jwtUtil.generateTokens(user.getUserId(), user.getRole(), accessToken,
			refreshToken);
		user.updateRefreshToken(refreshToken);
		return jwtTokenResponse;
	}

	private JwtTokenResponse generateTokensWithUpdateRefreshToken(User user, String name) {
		String refreshToken = user.getRefreshToken().split("|")[0];
		String accessToken = user.getRefreshToken().split("|")[1];
		JwtTokenResponse jwtTokenResponse = jwtUtil.generateTokens(user.getUserId(), user.getRole(), accessToken,
			refreshToken);
		user.updateRefreshToken(refreshToken);
		user.updateName(name);
		return jwtTokenResponse;
	}

	private String getToken(String token) {
		if (token.startsWith(Constants.BEARER_PREFIX)) {
			return token.substring(Constants.BEARER_PREFIX.length());
		} else {
			return token;
		}
	}

	@Transactional
	public void logout(UserAuthentication authentication) {
		System.out.println("JWT: token: " + authentication.getAccessToken());
		String accessToken = jwtUtil.getOriginalAccessToken(authentication.getAccessToken());
		System.out.println("orginal token: " + accessToken);
		String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		restTemplate.exchange(logoutUrl, HttpMethod.POST, entity, String.class);
		try {
			Long userId = Long.parseLong(authentication.getName());
			User user = userRepository.findByUserId(userId).get();
			user.updateRefreshToken("");
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("HTTP error while getting access token from Kakao: " + e.getStatusCode() + " - "
				+ e.getResponseBodyAsString(), e);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected error while getting access token from Kakao", e);
		}

	}

}