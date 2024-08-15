package com.server.booyoungee.domain.login.application;

import static com.server.booyoungee.domain.login.exception.LoginExceptionCode.*;

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
import com.server.booyoungee.domain.login.dto.request.KakaoLoginRequestDto;
import com.server.booyoungee.domain.login.dto.request.LoginRequestDto;
import com.server.booyoungee.domain.login.dto.request.SignUpRequestDto;
import com.server.booyoungee.domain.login.dto.response.JwtTokenResponse;
import com.server.booyoungee.domain.login.exception.ConflictUserException;
import com.server.booyoungee.domain.login.exception.NotFoundUserInfoException;
import com.server.booyoungee.domain.user.dao.UserRepository;
import com.server.booyoungee.domain.user.domain.User;
import com.server.booyoungee.global.exception.CustomException;
import com.server.booyoungee.global.oauth.security.info.UserAuthentication;
import com.server.booyoungee.global.utils.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("checkstyle:RegexpMultiline")
@Service
@RequiredArgsConstructor
public class AuthService {

	private final KakaoLoginService kakaoLoginService;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final RestTemplate restTemplate;

	@Transactional
	public JwtTokenResponse login(KakaoLoginRequestDto providerToken, LoginRequestDto request) throws IOException {
		SocialInfoDto socialInfo = getSocialInfo(request, providerToken.getAccessToken());
		User user = loadOrCreateUser(socialInfo);
		String refreshToken = providerToken.getRefreshToken();
		if (refreshToken == null) {
			refreshToken = user.getRefreshToken();
		}
		return generateTokensWithUpdateRefreshToken(user, providerToken.getAccessToken(), refreshToken);
	}

	private SocialInfoDto getSocialInfo(LoginRequestDto request, String providerToken) {
		if (request.provider().toString().equals(Provider.KAKAO.toString())) {
			return kakaoLoginService.getInfo(providerToken);
		} else {
			throw new CustomException(NOT_FOUND_USER_INFO);
		}
	}

	private User loadOrCreateUser(SocialInfoDto socialInfo) {
		return userRepository.findBySerialId(socialInfo.serialId())
			.orElseGet(() -> {
				throw new NotFoundUserInfoException(); //회원가입 필요 404
			});
	}

	@Transactional
	public JwtTokenResponse signup(SignUpRequestDto providerToken, String name, LoginRequestDto request) throws
		IOException {
		SocialInfoDto socialInfo = getSocialInfo(request, providerToken.getAccessToken());
		User user = createUser(providerToken, name, socialInfo);
		return generateTokensWithUpdateRefreshToken(user, providerToken.getAccessToken(), user.getRefreshToken());
	}

	private User createUser(SignUpRequestDto providerToken, String name, SocialInfoDto socialInfo) {
		Optional<User> existingUser = userRepository.findBySerialId(socialInfo.serialId());
		if (existingUser.isPresent()) {
			throw new ConflictUserException(); // Assuming USER_ALREADY_EXISTS is a defined error code
		}

		User newUser = User.builder()
			.serialId(socialInfo.serialId())
			.email("")
			.name(name)
			.role(User.Role.USER)
			.refreshToken(providerToken.getRefreshToken()) // Initialize refreshToken as an empty string
			.build();
		userRepository.save(newUser);
		return newUser;
	}

	private JwtTokenResponse generateTokensWithUpdateRefreshToken(User user, String accessToken, String refreshToken) {
		JwtTokenResponse jwtTokenResponse = jwtUtil.generateTokens(user.getUserId(), user.getRole(), accessToken,
			refreshToken);
		user.updateRefreshToken(refreshToken);
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
		String accessToken = jwtUtil.getOriginalAccessToken(authentication.getAccessToken());
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